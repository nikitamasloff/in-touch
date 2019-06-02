package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Completable

interface UnfollowProfileByIdUseCase {

    operator fun invoke(id: Profile.Id): Completable
}

class UnfollowProfileByIdUseCaseImpl(private val profileRepository: ProfileRepository) :
    UnfollowProfileByIdUseCase {

    override fun invoke(id: Profile.Id): Completable = profileRepository.unfollowProfileById(id)
}