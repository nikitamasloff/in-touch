package com.nikitamaslov.logindomain.interactor

import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.repository.LoginRepository
import io.reactivex.Completable

interface RestoreUseCase {

    operator fun invoke(token: Credential.Token): Completable
}

class RestoreUseCaseImpl(private val loginRepository: LoginRepository) : RestoreUseCase {

    override fun invoke(token: Credential.Token): Completable =
        loginRepository.restore(token)
}
