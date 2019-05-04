package com.nikitamaslov.core.rx.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulerProvider {

    val ui: Scheduler

    val io: Scheduler

    val computation: Scheduler
}

class RxSchedulerProviderImpl : RxSchedulerProvider {

    override val ui: Scheduler get() = AndroidSchedulers.mainThread()

    override val io: Scheduler get() = Schedulers.io()

    override val computation: Scheduler get() = Schedulers.computation()
}
