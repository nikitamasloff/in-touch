package com.nikitamaslov.intouch.di

import android.content.Context
import com.nikitamaslov.di.module.common.ActivityBuilderModule
import com.nikitamaslov.di.module.common.AppModule
import com.nikitamaslov.di.module.common.FragmentBuilderModule
import com.nikitamaslov.di.module.common.ViewModelBinderModule
import com.nikitamaslov.di.qualifier.ApplicationContext
import com.nikitamaslov.intouch.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ViewModelBinderModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            @Singleton
            @ApplicationContext
            applicationContext: Context
        ): AppComponent
    }
}
