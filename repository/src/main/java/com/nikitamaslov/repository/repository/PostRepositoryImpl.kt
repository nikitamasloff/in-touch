package com.nikitamaslov.repository.repository

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.postdomain.model.Optional
import com.nikitamaslov.postdomain.repository.PostRepository
import com.nikitamaslov.profiledomain.model.Profile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class PostRepositoryImpl : PostRepository {

    override fun observeAvatarByProfileId(profileId: Profile.Id): Observable<Optional<Avatar>> {

    }

    override fun getAvatarByProfileId(profileId: Profile.Id): Single<Optional<Avatar>> {
        TODO()
    }

    override fun setAvatar(avatarCreator: Avatar.Creator): Completable {
        TODO()
    }

    override fun deleteAvatar(): Completable {
        TODO()
    }
}