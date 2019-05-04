package com.nikitamaslov.di.module.common

import com.nikitamaslov.di.module.core.CoreModule
import dagger.Module

@Module(includes = [CoreModule::class])
abstract class AppModule
