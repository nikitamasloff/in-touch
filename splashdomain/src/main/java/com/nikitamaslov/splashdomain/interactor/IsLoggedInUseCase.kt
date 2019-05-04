package com.nikitamaslov.splashdomain.interactor

import com.nikitamaslov.splashdomain.repository.SplashRepository

interface IsLoggedInUseCase {

    operator fun invoke(): Boolean
}

class IsLoggedInUseCaseImpl(private val splashRepository: SplashRepository) : IsLoggedInUseCase {

    override fun invoke(): Boolean = splashRepository.isLoggedIn()
}
