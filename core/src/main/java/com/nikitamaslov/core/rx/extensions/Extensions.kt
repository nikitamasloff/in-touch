package com.nikitamaslov.core.rx.extensions

import io.reactivex.Completable

fun Completable.mapError(mapper: (t: Throwable) -> Throwable): Completable =
    this.onErrorResumeNext { t: Throwable -> Completable.error(mapper(t)) }
