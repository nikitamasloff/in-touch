package com.nikitamaslov.postdomain.interactor

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.postdomain.model.Optional
import com.nikitamaslov.postdomain.repository.PostRepository
import com.nikitamaslov.profiledomain.model.Profile
import io.reactivex.Single

interface GetAvatarByProfileIdUseCase {

    operator fun invoke(profileId: Profile.Id): Single<Optional<Avatar>>
}

class GetAvatarByProfileIdUseCaseImpl(private val profileRepository: PostRepository) :
    GetAvatarByProfileIdUseCase {

    override fun invoke(profileId: Profile.Id): Single<Optional<Avatar>> =
        profileRepository.getAvatarByProfileId(profileId)
}