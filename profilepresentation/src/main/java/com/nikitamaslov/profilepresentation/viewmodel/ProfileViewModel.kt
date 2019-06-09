package com.nikitamaslov.profilepresentation.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.lifecycle.extensions.map
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.exception.NetworkConnectionException
import com.nikitamaslov.profiledomain.exception.ServerException
import com.nikitamaslov.profiledomain.exception.SystemException
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.fragment.ProfileRelationsFragment
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

abstract class ProfileViewModel(
    protected val schedulers: RxSchedulerProvider,
    val navigator: Navigator
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val flags: MutableSet<String> = mutableSetOf()

    private val _isInitializing = MutableLiveData<Boolean>(true)
    val isInitializing: LiveData<Boolean> get() = _isInitializing

    val hasBeenInitialized: LiveData<Boolean> get() = _profile.map { it != null }

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasFatalError = MutableLiveData<Boolean>(false)
    val hasFatalError: LiveData<Boolean> get() = _hasFatalError

    private val _fatalErrorResId = MutableLiveData<Int>()
    val fatalErrorResId: LiveData<Int> get() = _fatalErrorResId

    private val _errorResId = MutableLiveData<StringResEvent?>(null)
    val errorResId: LiveData<StringResEvent?> get() = _errorResId

    private val _profile = MutableLiveData<Profile?>(null)
    protected val profile: LiveData<Profile?> get() = _profile

    val firstName: LiveData<String?>
        get() = _profile.map { it?.initials?.firstName }

    val lastName: LiveData<String?>
        get() = _profile.map { it?.initials?.lastName }

    val description: LiveData<String?>
        get() = _profile.map { it?.description?.src }

    val followersAmount: LiveData<String?>
        get() = _profile.map { it?.followersAmount?.toString() }

    val subscriptionsAmount: LiveData<String?>
        get() = _profile.map { it?.subscriptionsAmount?.toString() }


    protected fun observeProfile() {
        compositeDisposable += this.profileObservable
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isInitializing.value = true }
            .doAfterNext { if (_isInitializing.value == true) _isInitializing.value = false }
            .subscribeBy(
                onError = ::applyInitError,
                onNext = ::applyProfile
            )
    }

    @CallSuper
    open fun tryToFixFatalError() {
        _hasFatalError.value = false
        when {
            flags.contains(Flags.FATAL_INIT_ERROR) -> {
                observeProfile()
                flags.remove(Flags.FATAL_INIT_ERROR)
            }
        }
    }

    fun showFollowers() {
        val profileId = this.profile.value?.id?.src ?: return
        val direction = GraphMainDirections.toProfileRelations(
            profileId,
            mode = ProfileRelationsFragment.Mode.FOLLOWERS.id
        )
        navigator.navigateTo(direction)
    }

    fun showSubscriptions() {
        val profileId = this.profile.value?.id?.src ?: return
        val direction = GraphMainDirections.toProfileRelations(
            profileId,
            mode = ProfileRelationsFragment.Mode.SUBSCRIPTIONS.id
        )
        navigator.navigateTo(direction)
    }

    private fun applyProfile(profile: Profile) {
        this._profile.value = profile
    }

    private fun applyInitError(t: Throwable) {
        flags.add(Flags.FATAL_INIT_ERROR)
        if (t is SystemException) _fatalErrorResId.value = when (t) {
            is NetworkConnectionException -> R.string.error_message_no_network
            is ServerException -> R.string.error_message_server_problems
        }
        _hasFatalError.value = true
    }

    protected fun applySystemError(t: Throwable) {
        val resId = when (t) {
            is NetworkConnectionException -> R.string.error_message_no_network
            is ServerException -> R.string.error_message_server_problems
            else -> return
        }
        _errorResId.value = StringResEvent(resId)
    }

    @CallSuper
    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected fun addDisposable(block: () -> Disposable) {
        this.compositeDisposable += block()
    }

    protected fun setIsLoading(value: Boolean) {
        this._isLoading.value = value
    }

    protected fun setHasFatalError(value: Boolean) {
        this._hasFatalError.value = value
    }

    protected fun setFatalErrorResId(@StringRes value: Int) {
        this._fatalErrorResId.value = value
    }

    protected fun setErrorResId(@StringRes value: Int) {
        this._errorResId.value = StringResEvent(value)
    }

    protected abstract val profileObservable: Observable<Profile>

    private object Flags {
        const val FATAL_INIT_ERROR = "fatal_init_error"
    }
}