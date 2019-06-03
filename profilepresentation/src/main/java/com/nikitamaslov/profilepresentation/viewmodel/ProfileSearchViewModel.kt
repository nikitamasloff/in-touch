package com.nikitamaslov.profilepresentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.exception.NetworkConnectionException
import com.nikitamaslov.profiledomain.exception.ServerException
import com.nikitamaslov.profiledomain.interactor.SearchProfilesUseCase
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.fragment.ProfileRelationsFragmentDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class ProfileSearchViewModel(
    private val searchProfiles: SearchProfilesUseCase,
    private val schedulers: RxSchedulerProvider,
    val navigator: Navigator
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _foundedProfiles = MutableLiveData<List<Profile.Header>>(emptyList())
    val foundedProfiles: LiveData<List<Profile.Header>> get() = _foundedProfiles

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorResId = MutableLiveData<StringResEvent>()
    val errorResId: LiveData<StringResEvent> get() = _errorResId


    fun searchProfiles(query: CharSequence?) {
        if (query.isNullOrEmpty()) return

        compositeDisposable += searchProfiles.invoke(query.toString(), limit = 100)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(
                onError = ::applySystemError,
                onSuccess = ::applyFoundedProfiles
            )
    }

    fun showProfile(profile: Profile.Header) {
        val direction =
            if (profile.isMe) ProfileRelationsFragmentDirections.toMyProfile()
            else ProfileRelationsFragmentDirections.toOtherProfile(profile.id.src)
        navigator.navigateTo(direction)
    }

    private fun applyFoundedProfiles(profiles: List<Profile.Header>) {
        this._foundedProfiles.value = profiles
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
}
