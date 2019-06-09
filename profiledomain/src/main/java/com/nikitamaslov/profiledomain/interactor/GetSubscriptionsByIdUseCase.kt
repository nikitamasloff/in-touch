package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Single

interface GetSubscriptionsByIdUseCase {

    operator fun invoke(id: Profile.Id): Single<List<Profile.Header>>
}

class GetSubscriptionsByIdUseCaseImpl(private val profileRepository: ProfileRepository) :
    GetSubscriptionsByIdUseCase {

    override fun invoke(id: Profile.Id): Single<List<Profile.Header>> =
        profileRepository.getSubscriptionsById(id)
}