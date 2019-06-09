package com.nikitamaslov.di.module.common

import com.nikitamaslov.di.module.splash.SplashPresentationModule
import com.nikitamaslov.loginpresentation.fragment.LoginFragment
import com.nikitamaslov.loginpresentation.fragment.RegisterFragment
import com.nikitamaslov.loginpresentation.fragment.RestoreFragment
import com.nikitamaslov.profilepresentation.fragment.*
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

    @ContributesAndroidInjector
    abstract fun contributeMyProfileFragment(): MyProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeOtherProfileFragment(): OtherProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileEditFragment(): ProfileEditFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileRelationsFragment(): ProfileRelationsFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileSearchFragment(): ProfileSearchFragment
}
