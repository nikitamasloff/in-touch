package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Completable

interface ChangeDescriptionUseCase {

    operator fun invoke(description: Profile.Description): Completable
}

class ChangeDescriptionUseCaseImpl(private val profileRepository: ProfileRepository) :
    ChangeDescriptionUseCase {

    override fun invoke(description: Profile.Description): Completable =
        profileRepository.changeDescription(description)
}