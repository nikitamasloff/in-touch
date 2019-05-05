package com.nikitamaslov.di.module.common

import com.nikitamaslov.di.module.auth.AuthModule
import com.nikitamaslov.di.module.core.CoreModule
import com.nikitamaslov.di.module.database.DatabaseModule
import com.nikitamaslov.di.module.repository.RepositoryModule
import dagger.Module

@Module(
    includes = [
        CoreModule::class,
        RepositoryModule::class,
        AuthModule::class,
        DatabaseModule::class
    ]
)
abstract class AppModule
