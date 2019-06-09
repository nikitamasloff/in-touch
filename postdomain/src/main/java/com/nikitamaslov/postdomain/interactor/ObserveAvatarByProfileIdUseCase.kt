package com.nikitamaslov.postdomain.interactor

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.postdomain.model.Optional
import com.nikitamaslov.postdomain.repository.PostRepository
import com.nikitamaslov.profiledomain.model.Profile
import io.reactivex.Observable

interface ObserveAvatarByProfileIdUseCase {

    operator fun invoke(profileId: Profile.Id): Observable<Optional<Avatar>>
}

class ObserveAvatarByProfileIdUseCaseImpl(private val postRepository: PostRepository) :
    ObserveAvatarByProfileIdUseCase {

    override fun invoke(profileId: Profile.Id): Observable<Optional<Avatar>> =
        postRepository.observeAvatarByProfileId(profileId)
}