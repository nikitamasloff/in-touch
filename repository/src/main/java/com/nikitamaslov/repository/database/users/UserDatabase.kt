package com.nikitamaslov.repository.database.users

import com.nikitamaslov.repository.database.users.model.DatabaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface UserDatabase {

    fun createUser(id: String, creator: DatabaseUser.Creator): Completable

    fun changeInitials(id: String, initials: DatabaseUser.Initials): Completable

    fun changeDescription(id: String, description: DatabaseUser.Description): Completable

    fun observeUserById(id: String): Observable<DatabaseUser>

    fun getUserById(id: String): Single<DatabaseUser>

    fun followUserByAnother(userId: String, followerId: String): Completable

    fun unfollowUserByAnother(userId: String, followerId: String): Completable

    fun findUsersByInitials(query: String, limit: Int): Single<List<DatabaseUser.Header>>
}
