package com.nikitamaslov.di.module.database

import com.google.firebase.firestore.FirebaseFirestore
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.database.users.UserDatabaseImpl
import com.nikitamaslov.repository.database.users.UserDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideUserDatabase(
        firestore: FirebaseFirestore,
        networkManager: NetworkManager
    ): UserDatabase {
        return UserDatabaseImpl(firestore, networkManager)
    }
}
