package com.nikitamaslov.postdomain.interactor

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.postdomain.repository.PostRepository
import io.reactivex.Completable

interface SetAvatarUseCase {

    operator fun invoke(avatarCreator: Avatar.Creator): Completable
}

class SetAvatarUseCaseImpl(private val postRepository: PostRepository) : SetAvatarUseCase {

    override fun invoke(avatarCreator: Avatar.Creator): Completable =
        postRepository.setAvatar(avatarCreator)
}