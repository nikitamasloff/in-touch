package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Observable

interface ObserveProfileByIdUseCase {

    operator fun invoke(id: Profile.Id): Observable<Profile>
}

class ObserveProfileByIdUseCaseImpl(private val profileRepository: ProfileRepository) :
    ObserveProfileByIdUseCase {

    override fun invoke(id: Profile.Id): Observable<Profile> =
        profileRepository.observeProfileById(id)
}