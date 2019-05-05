package com.nikitamaslov.loginpresentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.logindomain.exception.*
import com.nikitamaslov.logindomain.interactor.RegisterUseCase
import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.model.User
import com.nikitamaslov.loginpresentation.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val schedulers: RxSchedulerProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
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

    fun register() {
        if (isLoading()) return

        val firstName = this.firstName.value
        val lastName = this.lastName.value
        val email = this.email.value
        val password = this.password.value
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty()
            || email.isNullOrEmpty() || password.isNullOrEmpty()
        ) {
            return
        }

        val user = User(firstName, lastName)
        val token = Credential.Token(email)
        val key = Credential.Key(password)
        val credential = Credential(token, key)

        compositeDisposable += registerUseCase(user, credential)
            .observeOn(schedulers.ui)
            .doOnSubscribe { setLoading(true) }
            .doFinally { setLoading(false) }
            .subscribeBy(
                onError = ::processErrorResponse,
                onComplete = ::processSuccessfulResponse
            )
    }

    private fun processSuccessfulResponse() {
        sendNotification(R.string.success_message_register)
    }

    private fun processErrorResponse(t: Throwable) {
        val stringResId = when (t) {
            is InvalidUserDataException -> R.string.error_message_invalid_user_data
            is InvalidCredentialTokenException -> R.string.error_message_invalid_email
            is InvalidCredentialKeyException -> R.string.error_message_invalid_password
            is CredentialTokenAlreadyExistsException -> R.string.error_message_user_already_exists
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
