package com.nikitamaslov.di.module.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.core.viewmodel.ViewModelFactory
import com.nikitamaslov.di.key.ViewModelKey
import com.nikitamaslov.di.module.login.LoginDomainModule
import com.nikitamaslov.di.module.splash.SplashDomainModule
import com.nikitamaslov.logindomain.interactor.LogInUseCase
import com.nikitamaslov.logindomain.interactor.RegisterUseCase
import com.nikitamaslov.logindomain.interactor.RestoreUseCase
import com.nikitamaslov.loginpresentation.viewmodel.LoginViewModel
import com.nikitamaslov.loginpresentation.viewmodel.RegisterViewModel
import com.nikitamaslov.loginpresentation.viewmodel.RestoreViewModel
import com.nikitamaslov.splashdomain.interactor.IsLoggedInUseCase
import com.nikitamaslov.splashpresentation.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        SplashDomainModule::class,
        LoginDomainModule::class
    ]
)
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

    @Provides
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun provideLoginViewModel(
        logInUseCase: LogInUseCase,
        schedulers: RxSchedulerProvider,
        navigator: Navigator
    ): ViewModel {
        return LoginViewModel(logInUseCase, navigator, schedulers)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    fun provideRegisterViewModel(
        registerUseCase: RegisterUseCase,
        schedulers: RxSchedulerProvider
    ): ViewModel {
        return RegisterViewModel(registerUseCase, schedulers)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RestoreViewModel::class)
    fun provideRestoreViewModel(
        restoreUseCase: RestoreUseCase,
        schedulers: RxSchedulerProvider
    ): ViewModel {
        return RestoreViewModel(restoreUseCase, schedulers)
    }
}
