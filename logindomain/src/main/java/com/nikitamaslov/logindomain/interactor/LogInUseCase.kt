package com.nikitamaslov.logindomain.interactor

import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.repository.LoginRepository
import io.reactivex.Completable

interface LogInUseCase {

    operator fun invoke(credential: Credential): Completable
}

class LogInUseCaseImpl(private val loginRepository: LoginRepository) : LogInUseCase {

    override fun invoke(credential: Credential): Completable =
        loginRepository.logIn(credential)
}
