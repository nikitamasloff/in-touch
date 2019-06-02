package com.nikitamaslov.core.rx.extensions

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

fun Completable.mapError(mapper: (t: Throwable) -> Throwable): Completable =
    this.onErrorResumeNext { t: Throwable -> Completable.error(mapper(t)) }

fun <T : Any> Observable<T>.mapError(mapper: (t: Throwable) -> Throwable): Observable<T> =
    this.onErrorResumeNext { t: Throwable -> Observable.error(mapper(t)) }

fun <T : Any> Single<T>.mapError(mapper: (t: Throwable) -> Throwable): Single<T> =
    this.onErrorResumeNext { t: Throwable -> Single.error(mapper(t)) }

fun <T : Any> Maybe<T>.mapError(mapper: (t: Throwable) -> Throwable): Maybe<T> =
    this.onErrorResumeNext { t: Throwable -> Maybe.error(mapper(t)) }