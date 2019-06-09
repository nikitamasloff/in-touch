package com.nikitamaslov.di.module.repository

import com.nikitamaslov.logindomain.repository.LoginRepository
import com.nikitamaslov.profiledomain.repository.ProfileAuthRepository
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.database.users.UserDatabase
import com.nikitamaslov.repository.repository.LoginRepositoryImpl
import com.nikitamaslov.repository.repository.ProfileAuthRepositoryImpl
import com.nikitamaslov.repository.repository.ProfileRepositoryImpl
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

    @Provides
    fun provideLoginRepository(
        auth: Auth,
        userDatabase: UserDatabase
    ): LoginRepository {
        return LoginRepositoryImpl(auth, userDatabase)
    }

    @Provides
    fun provideProfileRepository(
        auth: Auth,
        userDatabase: UserDatabase
    ): ProfileRepository {
        return ProfileRepositoryImpl(auth, userDatabase)
    }

    @Provides
    fun provideProfileAuthRepository(auth: Auth): ProfileAuthRepository {
        return ProfileAuthRepositoryImpl(auth)
    }
}
