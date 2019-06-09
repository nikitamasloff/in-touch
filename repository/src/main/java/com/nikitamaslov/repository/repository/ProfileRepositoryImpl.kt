package com.nikitamaslov.repository.repository

import com.nikitamaslov.core.rx.extensions.mapError
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.mapper.mapAuthToProfileCredentialException
import com.nikitamaslov.repository.database.users.UserDatabase
import com.nikitamaslov.repository.database.users.mapper.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class ProfileRepositoryImpl(
    private val auth: Auth,
    private val database: UserDatabase
) : ProfileRepository {

    private val currentUserId: String get() = auth.currentUser!!.id

    override fun changeInitials(initials: Profile.Initials): Completable =
        database.changeInitials(
            id = currentUserId,
            initials = initials.mapToDatabaseUserInitials()
        )
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun changeDescription(description: Profile.Description): Completable =
        database.changeDescription(
            id = currentUserId,
            description = description.mapToDatabaseUserDescription()
        )
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun observeMyProfile(): Observable<Profile> =
        database.observeUserById(currentUserId)
            .map { it.mapToProfile(currentUserId) }
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun observeProfileById(id: Profile.Id): Observable<Profile> =
        database.observeUserById(id.src)
            .map { it.mapToProfile(currentUserId) }
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun followProfileById(id: Profile.Id): Completable =
        database.followUserByAnother(
            userId = id.src,
            followerId = currentUserId
        )
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun unfollowProfileById(id: Profile.Id): Completable =
        database.unfollowUserByAnother(
            userId = id.src,
            followerId = currentUserId
        )
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun getFollowersById(id: Profile.Id): Single<List<Profile.Header>> =
        database.getUserById(id.src)
            .map { user -> user.followers.map { it.mapToProfileHeader(currentUserId) } }
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun getSubscriptionsById(id: Profile.Id): Single<List<Profile.Header>> =
        database.getUserById(id.src)
            .map { user -> user.subscriptions.map { it.mapToProfileHeader(currentUserId) } }
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)

    override fun searchProfiles(query: String, limit: Int): Single<List<Profile.Header>> =
        database.findUsersByInitials(query, limit)
            .map { user -> user.map { it.mapToProfileHeader(currentUserId) } }
            .mapError(::mapAuthToProfileCredentialException)
            .mapError(::mapDatabaseToProfileSystemException)
}