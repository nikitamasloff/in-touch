package com.nikitamaslov.logindomain.interactor

import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.model.User
import com.nikitamaslov.logindomain.repository.LoginRepository
import io.reactivex.Completable

interface RegisterUseCase {

    operator fun invoke(user: User, credential: Credential): Completable
}

class RegisterUseCaseImpl(private val loginRepository: LoginRepository) : RegisterUseCase {

    override fun invoke(user: User, credential: Credential): Completable =
        loginRepository.register(user, credential)
}
