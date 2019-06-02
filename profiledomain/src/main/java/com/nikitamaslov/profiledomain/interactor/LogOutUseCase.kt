package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import io.reactivex.Completable

interface LogOutUseCase {

    operator fun invoke(): Completable
}

class LogOutUseCaseImpl(private val profileAuthRepository: ProfileAuthRepository) : LogOutUseCase {

    override fun invoke(): Completable = profileAuthRepository.logOut()
}