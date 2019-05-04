package com.nikitamaslov.intouch

import com.nikitamaslov.di.app.BaseApplication
import com.nikitamaslov.intouch.di.AppInjector

class App : BaseApplication() {

    override fun performInjection() {
        AppInjector.inject(this)
    }
}
