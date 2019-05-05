package com.nikitamaslov.logindomain.repository

import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.model.User
import io.reactivex.Completable

interface LoginRepository {

    fun register(user: User, credential: Credential): Completable

    fun logIn(credential: Credential): Completable

    fun restore(token: Credential.Token): Completable
}
