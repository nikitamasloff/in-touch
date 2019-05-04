package com.nikitamaslov.intouch.di

import com.nikitamaslov.intouch.App

object AppInjector {

    fun inject(app: App) {
        DaggerAppComponent.factory()
            .create(applicationContext = app)
            .inject(app)
    }
}
