package com.nikitamaslov.di.module.core

import android.content.Context
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.navigation.NavigatorImpl
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.network.NetworkManagerImpl
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProviderImpl
import com.nikitamaslov.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class CoreModule {

    @Provides
    fun provideNetworkManager(@ApplicationContext applicationContext: Context): NetworkManager {
        return NetworkManagerImpl(applicationContext)
    }

    @Provides
    fun provideRxSchedulerProvider(): RxSchedulerProvider {
        return RxSchedulerProviderImpl()
    }

    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl()
    }
}
