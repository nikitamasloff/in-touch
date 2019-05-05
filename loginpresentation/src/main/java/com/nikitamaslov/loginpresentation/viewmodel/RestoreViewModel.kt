package com.nikitamaslov.loginpresentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.logindomain.exception.NetworkConnectionException
import com.nikitamaslov.logindomain.exception.NoSuchCredentialTokenException
import com.nikitamaslov.logindomain.exception.ServerException
import com.nikitamaslov.logindomain.interactor.RestoreUseCase
import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.loginpresentation.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class RestoreViewModel(
    private val restoreUseCase: RestoreUseCase,
    private val schedulers: RxSchedulerProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val recoveryEmail = MutableLiveData<String>()

    private val _status = MutableLiveData<Int>(null)
    val status: LiveData<Int> get() = _status

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private fun isLoading(): Boolean = _loading.value == true

    private fun setLoading(value: Boolean) {
        _loading.value = value
    }

    fun restore() {
        if (isLoading()) return

        val email = this.recoveryEmail.value
        if (email.isNullOrEmpty()) return

        val token = Credential.Token(email)

        compositeDisposable += restoreUseCase(token)
            .observeOn(schedulers.ui)
            .doOnSubscribe { setLoading(true) }
            .doFinally { setLoading(false) }
            .subscribeBy(
                onError = ::processErrorResponse,
                onComplete = ::processSuccessfulResponse
            )
    }

    private fun processSuccessfulResponse() {
        sendNotification(R.string.success_message_restore)
    }

    private fun processErrorResponse(t: Throwable) {
        val stringResId = when (t) {
            is NoSuchCredentialTokenException -> R.string.error_message_no_such_email
            is NetworkConnectionException -> R.string.error_message_no_internet_connection
            is ServerException -> R.string.error_message_server_unavailable
            else -> return
        }
        sendNotification(stringResId)
    }

    private fun sendNotification(@StringRes resId: Int) {
        _status.value = resId
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
