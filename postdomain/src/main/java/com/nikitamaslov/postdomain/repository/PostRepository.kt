package com.nikitamaslov.postdomain.repository

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.postdomain.model.Optional
import com.nikitamaslov.profiledomain.model.Profile
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface PostRepository {

    fun observeAvatarByProfileId(profileId: Profile.Id): Observable<Optional<Avatar>>

    fun getAvatarByProfileId(profileId: Profile.Id): Single<Optional<Avatar>>

    fun setAvatar(avatarCreator: Avatar.Creator): Completable

    fun deleteAvatar(): Completable
}