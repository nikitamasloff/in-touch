package com.nikitamaslov.repository.auth

import com.nikitamaslov.repository.auth.model.AuthCredential
import com.nikitamaslov.repository.auth.model.AuthUser
import io.reactivex.Completable

interface Auth {

    val currentUser: AuthUser?

    fun register(credential: AuthCredential): Completable

    fun logIn(credential: AuthCredential): Completable

    fun restore(credentialToken: AuthCredential.Token): Completable
}
