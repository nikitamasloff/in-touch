package com.nikitamaslov.profilepresentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.lifecycle.extensions.map
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.exception.*
import com.nikitamaslov.profiledomain.interactor.*
import com.nikitamaslov.profiledomain.model.Credential
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class ProfileEditViewModel(
    private val observeMyProfile: ObserveMyProfileUseCase,
    private val changeInitials: ChangeInitialsUseCase,
    private val changeDescription: ChangeDescriptionUseCase,
    private val changeCredentialToken: ChangeCredentialTokenUseCase,
    private val changeCredentialKey: ChangeCredentialKeyUseCase,
    private val schedulers: RxSchedulerProvider
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _myProfile = MutableLiveData<Profile?>(null)
    val myProfile: LiveData<Profile?> get() = _myProfile

    private val _isInitializing = MutableLiveData<Boolean>(false)
    val isInitializing: LiveData<Boolean> get() = _isInitializing

    val hasBeenInitialized: LiveData<Boolean> get() = _myProfile.map { it != null }

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasFatalError = MutableLiveData<Boolean>(false)
    val hasFatalError: LiveData<Boolean> get() = _hasFatalError

    private val _fatalErrorResId = MutableLiveData<Int>()
    val fatalErrorResId: LiveData<Int> get() = _fatalErrorResId

    private val _errorResId = MutableLiveData<StringResEvent>()
    val errorResId: LiveData<StringResEvent> get() = _errorResId

    private val flags: MutableSet<String> = mutableSetOf()


    init {
        observeProfile()
    }

    private fun observeProfile() {
        compositeDisposable += observeMyProfile()
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isInitializing.value = true }
            .doAfterNext { if (_isInitializing.value == true) _isInitializing.value = false }
            .subscribeBy(
                onError = ::applyInitError,
                onNext = ::applyProfile
            )
    }

    fun changeInitials(firstName: CharSequence?, lastName: CharSequence?) {
        if (firstName.isNullOrEmpty() || lastName.isNullOrEmpty()) return

        val initials = Profile.Initials(firstName.toString(), lastName.toString())
        compositeDisposable += changeInitials(initials)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(onError = ::applySystemError)
    }

    fun changeDescription(src: CharSequence?) {
        if (src.isNullOrEmpty()) return

        val description = Profile.Description(src.toString())
        compositeDisposable += changeDescription(description)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(onError = ::applySystemError)
    }

    fun changeEmail(newEmail: CharSequence?, password: CharSequence?) {
        if (newEmail.isNullOrEmpty() || password.isNullOrEmpty()) return

        val newCredentialToken = Credential.Token(newEmail.toString())
        val credentialKey = Credential.Key(password.toString())
        compositeDisposable += changeCredentialToken(newCredentialToken, credentialKey)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(onError = { t ->
                when (t) {
                    is CredentialException -> applyCredentialError(t)
                    is SystemException -> applySystemError(t)
                }
            })
    }

    fun changePassword(currentPassword: CharSequence?, newPassword: CharSequence?) {
        if (currentPassword.isNullOrEmpty() || newPassword.isNullOrEmpty()) return

        val currentCredentialKey = Credential.Key(currentPassword.toString())
        val newCredentialKey = Credential.Key(newPassword.toString())
        compositeDisposable += changeCredentialKey(currentCredentialKey, newCredentialKey)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(onError = { t ->
                when (t) {
                    is CredentialException -> applyCredentialError(t)
                    is SystemException -> applySystemError(t)
                }
            })
    }

    fun tryToFixFatalError() {
        _hasFatalError.value = false
        when {
            flags.contains(Flags.FATAL_INIT_ERROR) -> {
                observeProfile()
                flags.remove(Flags.FATAL_INIT_ERROR)
            }
        }
    }

    private fun applyProfile(profile: Profile) {
        this._myProfile.value = profile
    }

    private fun applyInitError(t: Throwable) {
        flags.add(Flags.FATAL_INIT_ERROR)
        _fatalErrorResId.value = when (t) {
            is NetworkConnectionException -> R.string.error_message_no_network
            is ServerException -> R.string.error_message_server_problems
            else -> return
        }
        _hasFatalError.value = true
    }

    private fun applyCredentialError(t: Throwable) {
        val resId = when (t) {
            is WrongCurrentCredentialKeyException ->
                R.string.error_message_wrong_current_credential_key
            is InvalidNewCredentialTokenException ->
                R.string.error_message_invalid_new_credential_token
            is InvalidNewCredentialKeyException ->
                R.string.error_message_invalid_new_credential_key
            is NewCredentialTokenCollisionException ->
                R.string.error_message_new_credential_token_collision
            else -> return
        }
        _errorResId.value = StringResEvent(resId)
    }

    private fun applySystemError(t: Throwable) {
        val resId = when (t) {
            is NetworkConnectionException -> R.string.error_message_no_network
            is ServerException -> R.string.error_message_server_problems
            else -> return
        }
        _errorResId.value = StringResEvent(resId)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    private object Flags {
        const val FATAL_INIT_ERROR = "fatal_init_error"
    }
}