package com.nikitamaslov.di.module.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.viewmodel.ViewModelFactory
import com.nikitamaslov.di.key.ViewModelKey
import com.nikitamaslov.di.module.splash.SplashDomainModule
import com.nikitamaslov.splashdomain.interactor.IsLoggedInUseCase
import com.nikitamaslov.splashpresentation.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [SplashDomainModule::class])
class ViewModelBinderModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }

    @Provides
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun provideSplashViewModel(
        isLoggedInUseCase: IsLoggedInUseCase,
        navigator: Navigator
    ): ViewModel {
        return SplashViewModel(isLoggedInUseCase, navigator)
    }
}
