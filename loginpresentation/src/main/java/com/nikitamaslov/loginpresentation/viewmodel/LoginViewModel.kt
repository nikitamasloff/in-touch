package com.nikitamaslov.loginpresentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.logindomain.exception.NetworkConnectionException
import com.nikitamaslov.logindomain.exception.ServerException
import com.nikitamaslov.logindomain.exception.WrongKeyException
import com.nikitamaslov.logindomain.exception.WrongTokenException
import com.nikitamaslov.logindomain.interactor.LogInUseCase
import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.loginpresentation.R
import com.nikitamaslov.loginpresentation.fragment.LoginFragmentDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(
    private val logInUseCase: LogInUseCase,
    val navigator: Navigator,
    private val schedulers: RxSchedulerProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _status = MutableLiveData<Int>(null)
    val status: LiveData<Int> get() = _status

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private fun isLoading(): Boolean = _loading.value == true

    private fun setLoading(value: Boolean) {
        _loading.value = value
    }

    fun logIn() {
        if (isLoading()) return

        val email = this.email.value
        val password = this.password.value
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) return

        val token = Credential.Token(email)
        val key = Credential.Key(password)
        val credential = Credential(token, key)

        compositeDisposable += logInUseCase(credential)
            .observeOn(schedulers.ui)
            .doOnSubscribe { setLoading(true) }
            .doFinally { setLoading(false) }
            .subscribeBy(
                onError = ::processErrorResponse,
                onComplete = ::processSuccessfulResponse
            )
    }

    private fun processSuccessfulResponse() {
        navigateToFeed()
    }

    private fun processErrorResponse(t: Throwable) {
        val stringResId = when (t) {
            is WrongTokenException -> R.string.error_message_wrong_email
            is WrongKeyException -> R.string.error_message_wrong_password
            is NetworkConnectionException -> R.string.error_message_no_internet_connection
            is ServerException -> R.string.error_message_server_unavailable
            else -> return
        }
        sendNotification(stringResId)
    }

    private fun sendNotification(@StringRes resId: Int) {
        _status.value = resId
    }

    fun navigateToRegister() {
        val direction = LoginFragmentDirections.toFragmentRegister()
        navigator.navigateTo(direction)
    }

    fun navigateToRestore() {
        val direction = LoginFragmentDirections.toFragmentRestore()
        navigator.navigateTo(direction)
    }

    private fun navigateToFeed() {
        val direction = LoginFragmentDirections.toGraphMain()
        navigator.navigateTo(direction)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
