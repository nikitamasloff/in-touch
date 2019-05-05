package com.nikitamaslov.di.module.common

import com.nikitamaslov.splashpresentation.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}
