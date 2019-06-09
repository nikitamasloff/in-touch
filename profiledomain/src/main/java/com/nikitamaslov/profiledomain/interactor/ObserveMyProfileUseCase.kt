package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Observable

interface ObserveMyProfileUseCase {

    operator fun invoke(): Observable<Profile>
}

class ObserveMyProfileUseCaseImpl(private val profileRepository: ProfileRepository) :
    ObserveMyProfileUseCase {

    override fun invoke(): Observable<Profile> = profileRepository.observeMyProfile()
}