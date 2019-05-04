package com.nikitamaslov.di.module.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelBinderModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }
}
