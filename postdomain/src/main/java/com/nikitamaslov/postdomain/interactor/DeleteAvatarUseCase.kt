package com.nikitamaslov.postdomain.interactor

import com.nikitamaslov.postdomain.repository.PostRepository
import io.reactivex.Completable

interface DeleteAvatarUseCase {

    operator fun invoke(): Completable
}

class DeleteAvatarUseCaseImpl(private val postRepository: PostRepository) : DeleteAvatarUseCase {

    override fun invoke(): Completable = postRepository.deleteAvatar()
}