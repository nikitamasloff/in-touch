package com.nikitamaslov.profilepresentation.viewmodel

import androidx.lifecycle.LiveData
import com.nikitamaslov.core.lifecycle.extensions.map
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.interactor.FollowProfileByIdUseCase
import com.nikitamaslov.profiledomain.interactor.ObserveProfileByIdUseCase
import com.nikitamaslov.profiledomain.interactor.UnfollowProfileByIdUseCase
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class OtherProfileViewModel(
    private val observeProfileById: ObserveProfileByIdUseCase,
    private val followProfileById: FollowProfileByIdUseCase,
    private val unfollowProfileById: UnfollowProfileByIdUseCase,
    schedulers: RxSchedulerProvider,
    navigator: Navigator
) : ProfileViewModel(schedulers, navigator) {

    override lateinit var profileObservable: Observable<Profile>

    private var profileId: Profile.Id? = null

    private val isSubscription: LiveData<Boolean?>
        get() = profile.map { it?.relation?.isSubscription }

    val isFollower: LiveData<Boolean?>
        get() = profile.map { it?.relation?.isFollower }

    val followOrUnfollowTitleResId: LiveData<Int?>
        get() = isSubscription.map {
            when (it) {
                true -> R.string.action_unfollow
                false -> R.string.action_follow
                else -> null
            }
        }

    fun initProfileId(id: String) {
        if (this.profileId == null) {
            this.profileId = Profile.Id(id)
            profileObservable = observeProfileById(this.profileId!!)
            observeProfile()
        }
    }

    fun followOrUnfollow() {
        val profile = this.profile.value ?: return

        val action =
            when (profile.relation?.isSubscription) {
                true -> unfollowProfileById(profile.id)
                false -> followProfileById(profile.id)
                else -> return
            }

        addDisposable {
            action
                .observeOn(schedulers.ui)
                .doOnSubscribe { setIsLoading(true) }
                .doFinally { setIsLoading(false) }
                .subscribeBy(onError = ::applySystemError)
        }
    }
}