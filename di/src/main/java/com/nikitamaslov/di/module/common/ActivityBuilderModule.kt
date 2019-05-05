package com.nikitamaslov.di.module.common

import com.nikitamaslov.loginpresentation.activity.LoginActivity
import com.nikitamaslov.splashpresentation.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity
}
