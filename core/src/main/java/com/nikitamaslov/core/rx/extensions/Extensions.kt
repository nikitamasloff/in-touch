package com.nikitamaslov.core.rx.extensions

import io.reactivex.*

fun Completable.mapError(mapper: (t: Throwable) -> Throwable): Completable =
    this.onErrorResumeNext { t: Throwable -> Completable.error(mapper(t)) }

fun <T : Any> Observable<T>.mapError(mapper: (t: Throwable) -> Throwable): Observable<T> =
    this.onErrorResumeNext { t: Throwable -> Observable.error(mapper(t)) }

fun <T : Any> Single<T>.mapError(mapper: (t: Throwable) -> Throwable): Single<T> =
    this.onErrorResumeNext { t: Throwable -> Single.error(mapper(t)) }

fun <T : Any> Maybe<T>.mapError(mapper: (t: Throwable) -> Throwable): Maybe<T> =
    this.onErrorResumeNext { t: Throwable -> Maybe.error(mapper(t)) }


fun CompletableEmitter?.safeOnComplete() {
    if (this != null && !isDisposed) {
        onComplete()
    }
}

fun CompletableEmitter?.safeOnError(t: Throwable) {
    if (this != null && !isDisposed) {
        onError(t)
    }
}

fun <T : Any> ObservableEmitter<T>?.safeOnNext(value: T) {
    if (this != null && !isDisposed) {
        onNext(value)
    }
}

fun <T : Any> ObservableEmitter<T>?.safeOnComplete() {
    if (this != null && !isDisposed) {
        onComplete()
    }
}

fun <T : Any> ObservableEmitter<T>?.safeOnError(t: Throwable) {
    if (this != null && !isDisposed) {
        onError(t)
    }
}

fun <T : Any> SingleEmitter<T>?.safeOnSuccess(value: T) {
    if (this != null && !isDisposed) {
        onSuccess(value)
    }
}

fun <T : Any> SingleEmitter<T>?.safeOnError(t: Throwable) {
    if (this != null && !isDisposed) {
        onError(t)
    }
}