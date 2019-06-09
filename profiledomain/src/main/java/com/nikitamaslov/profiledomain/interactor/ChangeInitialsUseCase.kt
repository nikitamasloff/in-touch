package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Completable

interface ChangeInitialsUseCase {

    operator fun invoke(initials: Profile.Initials): Completable
}

class ChangeInitialsUseCaseImpl(private val profileRepository: ProfileRepository) :
    ChangeInitialsUseCase {

    override fun invoke(initials: Profile.Initials): Completable =
        profileRepository.changeInitials(initials)
}