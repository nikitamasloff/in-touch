package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Completable

interface FollowProfileByIdUseCase {

    operator fun invoke(id: Profile.Id): Completable
}

class FollowProfileByIdUseCaseImpl(private val profileRepository: ProfileRepository) :
    FollowProfileByIdUseCase {

    override fun invoke(id: Profile.Id): Completable = profileRepository.followProfileById(id)
}