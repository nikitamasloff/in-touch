package com.nikitamaslov.database.users

import com.google.firebase.firestore.FirebaseFirestore
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.database.users.mapper.asFieldsMap
import com.nikitamaslov.repository.database.exception.DatabaseNetworkConnectionException
import com.nikitamaslov.repository.database.exception.DatabaseServerException
import com.nikitamaslov.repository.database.users.UserDatabase
import com.nikitamaslov.repository.database.users.model.DatabaseUserCreator
import io.reactivex.Completable

private const val COLLECTION_USERS = "Users"

class UserDatabaseImpl(
    private val firestore: FirebaseFirestore,
    private val schedulers: RxSchedulerProvider,
    private val networkManager: NetworkManager
) : UserDatabase {

    override fun createUser(id: String, creator: DatabaseUserCreator): Completable =
        Completable.create { emitter ->
            firestore.collection(COLLECTION_USERS)
                .document(id)
                .set(creator.asFieldsMap())
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(systemException()) }
        }
            .subscribeOn(schedulers.io)

    private fun systemException() =
        if (!networkManager.isConnected) DatabaseNetworkConnectionException()
        else DatabaseServerException()
}
