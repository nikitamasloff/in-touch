package com.nikitamaslov.profiledomain.interactor

import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profiledomain.repository.ProfileRepository
import io.reactivex.Single

interface SearchProfilesUseCase {

    operator fun invoke(query: String, limit: Int): Single<List<Profile.Header>>
}

class SearchProfilesUseCaseImpl(private val profileRepository: ProfileRepository) :
    SearchProfilesUseCase {

    override fun invoke(query: String, limit: Int): Single<List<Profile.Header>> =
        profileRepository.searchProfiles(query, limit)
}