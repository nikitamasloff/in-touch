package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Credential
import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import io.reactivex.Completable

interface ChangeCredentialTokenUseCase {

    operator fun invoke(
        newCredentialToken: Credential.Token,
        credentialKey: Credential.Key
    ): Completable
}

class ChangeCredentialTokenUseCaseImpl(private val profileAuthRepository: ProfileAuthRepository) :
    ChangeCredentialTokenUseCase {

    override fun invoke(
        newCredentialToken: Credential.Token,
        credentialKey: Credential.Key
    ): Completable =
        profileAuthRepository.changeCredentialToken(newCredentialToken, credentialKey)
}