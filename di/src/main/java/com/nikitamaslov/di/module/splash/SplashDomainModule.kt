package com.nikitamaslov.di.module.splash

import com.nikitamaslov.splashdomain.interactor.IsLoggedInUseCase
import com.nikitamaslov.splashdomain.interactor.IsLoggedInUseCaseImpl
import com.nikitamaslov.splashdomain.repository.SplashRepository
import dagger.Module
import dagger.Provides

@Module
class SplashDomainModule {

    @Provides
    fun provideIsLoggedInUseCase(splashRepository: SplashRepository): IsLoggedInUseCase {
        return IsLoggedInUseCaseImpl(splashRepository)
    }
}
