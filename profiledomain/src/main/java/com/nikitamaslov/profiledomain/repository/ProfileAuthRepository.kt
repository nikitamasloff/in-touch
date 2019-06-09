package com.nikitamaslov.profiledomain.repository

import com.nikitamaslov.profiledomain.model.Credential
import io.reactivex.Completable

interface ProfileAuthRepository {

    fun changeCredentialToken(
        newCredentialToken: Credential.Token,
        key: Credential.Key
    ): Completable

    fun changeCredentialKey(
        currentCredentialKey: Credential.Key,
        newCredentialKey: Credential.Key
    ): Completable

    fun logOut(): Completable
}