package com.nikitamaslov.di.module.common

import com.nikitamaslov.di.module.splash.SplashPresentationModule
import com.nikitamaslov.loginpresentation.fragment.LoginFragment
import com.nikitamaslov.loginpresentation.fragment.RegisterFragment
import com.nikitamaslov.loginpresentation.fragment.RestoreFragment
import com.nikitamaslov.splashpresentation.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [SplashPresentationModule::class])
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeRestoreFragment(): RestoreFragment

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment
}
