package com.nikitamaslov.profilepresentation.viewmodel

import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.profiledomain.interactor.LogOutUseCase
import com.nikitamaslov.profiledomain.interactor.ObserveMyProfileUseCase
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.fragment.MyProfileFragmentDirections
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy

class MyProfileViewModel(
    private val observeMyProfile: ObserveMyProfileUseCase,
    private val logOut: LogOutUseCase,
    schedulers: RxSchedulerProvider,
    navigator: Navigator
) : ProfileViewModel(schedulers, navigator) {

    init {
        observeProfile()
    }

    override val profileObservable: Observable<Profile> get() = observeMyProfile()

    fun edit() {
        val direction = MyProfileFragmentDirections.toProfileEdit()
        navigator.navigateTo(direction)
    }

    fun logOut() {
        addDisposable {
            logOut.invoke()
                .doOnSubscribe { setIsLoading(true) }
                .doFinally { setIsLoading(false) }
                .subscribeBy(onComplete = {
                    val direction = GraphMainDirections.logOut()
                    navigator.navigateTo(direction)
                })
        }
    }
}
