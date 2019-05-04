package com.nikitamaslov.di.module.splash

import android.os.Handler
import dagger.Module
import dagger.Provides

@Module
class SplashPresentationModule {

    @Provides
    fun provideHandler(): Handler {
        return Handler()
    }
}
