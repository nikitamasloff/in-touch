package com.nikitamaslov.repository.database.users

import com.nikitamaslov.repository.database.users.model.DatabaseUserCreator
import io.reactivex.Completable

interface UserDatabase {

    fun createUser(id: String, creator: DatabaseUserCreator): Completable
}
