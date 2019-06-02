package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Credential
import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import io.reactivex.Completable

interface ChangeCredentialKeyUseCase {

    operator fun invoke(
        currentCredentialKey: Credential.Key,
        newCredentialKey: Credential.Key
    ): Completable
}

class ChangeCredentialKeyUseCaseImpl(private val profileAuthRepository: ProfileAuthRepository) :
    ChangeCredentialKeyUseCase {

    override fun invoke(
        currentCredentialKey: Credential.Key,
        newCredentialKey: Credential.Key
    ): Completable =
        profileAuthRepository.changeCredentialKey(currentCredentialKey, newCredentialKey)
}