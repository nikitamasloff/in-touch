package com.nikitamaslov.profilepresentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.exception.NetworkConnectionException
import com.nikitamaslov.profiledomain.exception.ServerException
import com.nikitamaslov.profiledomain.interactor.GetFollowersByIdUseCase
import com.nikitamaslov.profiledomain.interactor.GetSubscriptionsByIdUseCase
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.fragment.ProfileRelationsFragmentDirections
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class ProfileRelationsViewModel(
    private val getFollowersById: GetFollowersByIdUseCase,
    private val getSubscriptionsById: GetSubscriptionsByIdUseCase,
    private val schedulers: RxSchedulerProvider,
    val navigator: Navigator
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var profileId: Profile.Id? = null

    private val _followers = MutableLiveData<List<Profile.Header>>()
    val followers: LiveData<List<Profile.Header>> get() = _followers

    private val _subscriptions = MutableLiveData<List<Profile.Header>>()
    val subscriptions: LiveData<List<Profile.Header>> get() = _subscriptions

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorResId = MutableLiveData<StringResEvent>()
    val errorResId: LiveData<StringResEvent> get() = _errorResId


    fun initProfileId(id: String) {
        if (this.profileId == null) this.profileId = Profile.Id(id)
    }

    fun queryFollowers() {
        checkNotNull(this.profileId)

        compositeDisposable += getFollowersById(this.profileId!!)
            .observeOn(schedulers.ui)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(
                onError = ::applySystemError,
                onSuccess = ::applyFollowers
            )
    }

    fun querySubscriptions() {
        checkNotNull(this.profileId)

        compositeDisposable += getSubscriptionsById(this.profileId!!)
            .doOnSubscribe { _isLoading.value = true }
            .doFinally { _isLoading.value = false }
            .subscribeBy(
                onError = ::applySystemError,
                onSuccess = ::applySubscriptions
            )
    }

    fun showProfile(profile: Profile.Header) {
        val direction =
            if (profile.isMe) ProfileRelationsFragmentDirections.toMyProfile()
            else ProfileRelationsFragmentDirections.toOtherProfile(profile.id.src)
        navigator.navigateTo(direction)
    }

    private fun applyFollowers(followers: List<Profile.Header>) {
        this._followers.value = followers
    }

    private fun applySubscriptions(subscriptions: List<Profile.Header>) {
        this._subscriptions.value = subscriptions
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
