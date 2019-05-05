package com.nikitamaslov.di.module.repository

import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.repository.SplashRepositoryImpl
import com.nikitamaslov.splashdomain.repository.SplashRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideSplashRepository(auth: Auth): SplashRepository {
        return SplashRepositoryImpl(auth)
    }
}
