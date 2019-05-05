package com.nikitamaslov.di.module.common

import com.nikitamaslov.di.module.splash.SplashPresentationModule
import com.nikitamaslov.splashpresentation.fragment.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [SplashPresentationModule::class])
    abstract fun contributeSplashFragment(): SplashFragment
}
