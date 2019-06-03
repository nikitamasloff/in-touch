package com.nikitamaslov.database.users

import com.google.firebase.firestore.*
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.rx.extensions.safeOnComplete
import com.nikitamaslov.core.rx.extensions.safeOnError
import com.nikitamaslov.core.rx.extensions.safeOnNext
import com.nikitamaslov.core.rx.extensions.safeOnSuccess
import com.nikitamaslov.database.users.mapper.asDatabaseUser
import com.nikitamaslov.database.users.mapper.asDatabaseUserHeader
import com.nikitamaslov.database.users.mapper.asFieldsMap
import com.nikitamaslov.database.users.model.UserFields
import com.nikitamaslov.repository.database.exception.DatabaseNetworkConnectionException
import com.nikitamaslov.repository.database.exception.DatabaseServerException
import com.nikitamaslov.repository.database.users.UserDatabase
import com.nikitamaslov.repository.database.users.model.DatabaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

private const val COLLECTION_USERS = "Users"

class UserDatabaseImpl(
    private val firestore: FirebaseFirestore,
    private val networkManager: NetworkManager
) : UserDatabase {

    override fun createUser(id: String, creator: DatabaseUser.Creator): Completable =
        Completable.create { emitter ->
            firestore.collection(COLLECTION_USERS)
                .document(id)
                .set(creator.asFieldsMap(id))
                .addOnSuccessListener { emitter.safeOnComplete() }
                .addOnFailureListener { emitter.safeOnError(systemException()) }
        }

    override fun changeInitials(id: String, initials: DatabaseUser.Initials): Completable =
        Completable.create { emitter ->
            firestore.collection(COLLECTION_USERS)
                .document(id)
                .update(initials.asFieldsMap())
                .addOnSuccessListener { emitter.safeOnComplete() }
                .addOnFailureListener { emitter.safeOnError(systemException()) }
        }

    override fun changeDescription(id: String, description: DatabaseUser.Description): Completable =
        Completable.create { emitter ->
            firestore.collection(COLLECTION_USERS)
                .document(id)
                .update(description.asFieldsMap())
                .addOnSuccessListener { emitter.safeOnComplete() }
                .addOnFailureListener { emitter.safeOnError(systemException()) }
        }

    override fun getUserById(id: String): Single<DatabaseUser> =
        Single.create { emitter ->
            firestore.collection(COLLECTION_USERS)
                .document(id)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val databaseUser = documentSnapshot.data!!.asDatabaseUser(documentSnapshot.id)
                    emitter.safeOnSuccess(databaseUser)
                }
                .addOnFailureListener {
                    emitter.safeOnError(systemException())
                }
        }

    override fun observeUserById(id: String): Observable<DatabaseUser> {
        var registration: ListenerRegistration? = null

        return Observable.create<DatabaseUser> { emitter ->
            val document = firestore.collection(COLLECTION_USERS)
                .document(id)

            registration = document.addSnapshotListener { snapshot, e ->
                when {
                    e != null -> emitter.safeOnError(systemException())
                    snapshot != null && snapshot.exists() -> {
                        val databaseUser = snapshot.data!!.asDatabaseUser(snapshot.id)
                        emitter.safeOnNext(databaseUser)
                    }
                }
            }
        }.doFinally { registration?.remove() }
    }

    override fun followUserByAnother(userId: String, followerId: String): Completable =
        Completable.create { emitter ->
            val userDocRef = firestore.collection(COLLECTION_USERS).document(userId)
            val followerDocRef = firestore.collection(COLLECTION_USERS).document(followerId)

            firestore.runTransaction { transaction ->
                val userDocSnapshot = transaction.get(userDocRef)
                val followerDocSnapshot = transaction.get(followerDocRef)

                val user = userDocSnapshot.let { it.data!!.asDatabaseUserHeader(it.id) }
                val follower = followerDocSnapshot.let { it.data!!.asDatabaseUserHeader(it.id) }

                transaction.update(
                    userDocRef,
                    "${UserFields.FOLLOWERS}.$followerId",
                    follower.asFieldsMap()
                )

                transaction.update(
                    followerDocRef,
                    "${UserFields.SUBSCRIPTIONS}.$userId",
                    user.asFieldsMap()
                )

                //Success
                null
            }
                .addOnSuccessListener { emitter.safeOnComplete() }
                .addOnFailureListener { emitter.safeOnError(systemException()) }
        }

    override fun unfollowUserByAnother(userId: String, followerId: String): Completable =
        Completable.create { emitter ->
            val userDocRef = firestore.collection(COLLECTION_USERS).document(userId)
            val followerDocRef = firestore.collection(COLLECTION_USERS).document(followerId)

            firestore.runTransaction { transaction ->
                transaction.update(
                    userDocRef,
                    "${UserFields.FOLLOWERS}.$followerId",
                    FieldValue.delete()
                )

                transaction.update(
                    followerDocRef,
                    "${UserFields.SUBSCRIPTIONS}.$userId",
                    FieldValue.delete()
                )

                //Success
                null
            }
                .addOnSuccessListener { emitter.safeOnComplete() }
                .addOnFailureListener { emitter.safeOnError(systemException()) }
        }

    override fun findUsersByInitials(
        query: String,
        limit: Int
    ): Single<List<DatabaseUser.Header>> = query.words().let { filters ->
        when {
            filters.isEmpty() -> Single.just(emptyList())
            filters.size == 1 -> findUsersByFilter(
                filter = filters.first(),
                limit = limit
            )
            else -> findUsersByFilter(
                filter1 = filters[0],
                filter2 = filters[1],
                limit = limit
            )
        }
    }

    private fun findUsersByFilter(
        filter: String,
        limit: Int? = null
    ): Single<List<DatabaseUser.Header>> =
        Single.zip(
            findUsersByCondition(limit) {
                whereGreaterThanOrEqualTo(UserFields.FIRST_NAME, filter)
                    .whereLessThan(UserFields.FIRST_NAME, filter.next())
            },
            findUsersByCondition(limit) {
                whereGreaterThanOrEqualTo(UserFields.LAST_NAME, filter)
                    .whereLessThan(UserFields.LAST_NAME, filter.next())
            },
            BiFunction { t1, t2 -> t1 + t2 }
        )

    private fun findUsersByFilter(
        filter1: String,
        filter2: String,
        limit: Int? = null
    ): Single<List<DatabaseUser.Header>> =
        Single.zip(
            findUsersByCondition(limit) {
                whereEqualTo(UserFields.FIRST_NAME, filter1)
                    .whereGreaterThanOrEqualTo(UserFields.LAST_NAME, filter2)
                    .whereLessThan(UserFields.LAST_NAME, filter2.next())
            },
            findUsersByCondition(limit) {
                whereEqualTo(UserFields.LAST_NAME, filter1)
                    .whereGreaterThanOrEqualTo(UserFields.FIRST_NAME, filter2)
                    .whereLessThan(UserFields.FIRST_NAME, filter2.next())
            },
            BiFunction { t1, t2 -> t1 + t2 }
        )

    private fun findUsersByCondition(
        limit: Int? = null,
        condition: CollectionReference.() -> Query
    ): Single<List<DatabaseUser.Header>> = Single.create { emitter ->
        firestore.collection(COLLECTION_USERS)
            .condition()
            .apply { if (limit != null) this.limit(limit.toLong()) }
            .get()
            .addOnSuccessListener { querySnapshot ->
                val documents = querySnapshot.documents
                val users = documents.map { it.data!!.asDatabaseUserHeader(it.id) }
                emitter.safeOnSuccess(users)
            }
            .addOnFailureListener {
                emitter.safeOnError(systemException())
            }
    }

    private fun String.words(): List<String> =
        split(' ')
            .asSequence()
            .map(String::trim)
            .filter(String::isNotEmpty)
            .filter(String::isNotBlank)
            .toList()

    private fun String.next(): String {
        if (isEmpty() || isBlank()) return this
        val last = this.last()
        val lastInc = last.inc()
        return dropLast(1) + lastInc
    }

    private fun systemException() =
        if (!networkManager.isConnected) DatabaseNetworkConnectionException()
        else DatabaseServerException()
}
