package com.nikitamaslov.repository.repository

import com.nikitamaslov.core.rx.extensions.mapError
import com.nikitamaslov.profiledomain.model.Credential
import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.mapper.mapAuthToProfileCredentialException
import com.nikitamaslov.repository.auth.mapper.mapToAuthCredentialKey
import com.nikitamaslov.repository.auth.mapper.mapToAuthCredentialToken
import io.reactivex.Completable

class ProfileAuthRepositoryImpl(private val auth: Auth) : ProfileAuthRepository {

    override fun changeCredentialToken(
        newCredentialToken: Credential.Token,
        key: Credential.Key
    ): Completable =
        auth.changeCredentialToken(
            newToken = newCredentialToken.mapToAuthCredentialToken(),
            key = key.mapToAuthCredentialKey()
        )
            .mapError(::mapAuthToProfileCredentialException)

    override fun changeCredentialKey(
        currentCredentialKey: Credential.Key,
        newCredentialKey: Credential.Key
    ): Completable =
        auth.changeCredentialKey(
            currentKey = currentCredentialKey.mapToAuthCredentialKey(),
            newKey = newCredentialKey.mapToAuthCredentialKey()
        )
            .mapError(::mapAuthToProfileCredentialException)

    override fun logOut(): Completable = auth.logOut()
}