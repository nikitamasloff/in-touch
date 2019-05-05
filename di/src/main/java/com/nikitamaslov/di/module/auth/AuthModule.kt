package com.nikitamaslov.di.module.auth

import com.google.firebase.auth.FirebaseAuth
import com.nikitamaslov.auth.AuthImpl
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.repository.auth.Auth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuth(
        firebaseAuth: FirebaseAuth,
        schedulers: RxSchedulerProvider,
        networkManager: NetworkManager
    ): Auth {
        return AuthImpl(firebaseAuth, schedulers, networkManager)
    }
}
