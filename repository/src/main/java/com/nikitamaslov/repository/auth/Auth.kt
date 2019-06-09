package com.nikitamaslov.repository.auth

import com.nikitamaslov.repository.auth.model.AuthCredential
import com.nikitamaslov.repository.auth.model.AuthUser
import io.reactivex.Completable
import io.reactivex.Single

interface Auth {

    val currentUser: AuthUser?

    fun register(credential: AuthCredential): Single<String>

    fun logIn(credential: AuthCredential): Completable

    fun restore(credentialToken: AuthCredential.Token): Completable

    fun changeCredentialToken(
        newToken: AuthCredential.Token,
        key: AuthCredential.Key
    ): Completable

    fun changeCredentialKey(
        currentKey: AuthCredential.Key,
        newKey: AuthCredential.Key
    ): Completable

    fun logOut(): Completable
}
