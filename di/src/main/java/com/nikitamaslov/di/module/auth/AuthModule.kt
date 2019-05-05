package com.nikitamaslov.di.module.auth

import com.google.firebase.auth.FirebaseAuth
import com.nikitamaslov.auth.AuthImpl
import com.nikitamaslov.repository.auth.Auth
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideAuth(firebaseAuth: FirebaseAuth): Auth {
        return AuthImpl(firebaseAuth)
    }
}
