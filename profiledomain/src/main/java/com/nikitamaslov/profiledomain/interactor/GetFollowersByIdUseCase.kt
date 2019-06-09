package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Single

interface GetFollowersByIdUseCase {

    operator fun invoke(id: Profile.Id): Single<List<Profile.Header>>
}

class GetFollowersByIdUseCaseImpl(private val profileRepository: ProfileRepository) :
    GetFollowersByIdUseCase {

    override fun invoke(id: Profile.Id): Single<List<Profile.Header>> =
        profileRepository.getFollowersById(id)
}