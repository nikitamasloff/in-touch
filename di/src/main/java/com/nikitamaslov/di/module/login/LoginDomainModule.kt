package com.nikitamaslov.di.module.login

import com.nikitamaslov.logindomain.interactor.*
import com.nikitamaslov.logindomain.repository.LoginRepository
import dagger.Module
import dagger.Provides

@Module
class LoginDomainModule {

    @Provides
    fun provideLogInUseCase(loginRepository: LoginRepository): LogInUseCase {
        return LogInUseCaseImpl(loginRepository)
    }

    @Provides
    fun provideRestoreUseCase(loginRepository: LoginRepository): RestoreUseCase {
        return RestoreUseCaseImpl(loginRepository)
    }

    @Provides
    fun provideRegisterUseCase(loginRepository: LoginRepository): RegisterUseCase {
        return RegisterUseCaseImpl(loginRepository)
    }
}
