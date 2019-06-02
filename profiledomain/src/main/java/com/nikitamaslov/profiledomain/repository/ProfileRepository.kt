package com.nikitamaslov.profiledomain.repository

import com.nikitamaslov.profiledomain.model.Profile
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface ProfileRepository {

    fun changeInitials(initials: Profile.Initials): Completable

    fun changeDescription(description: Profile.Description): Completable

    fun observeMyProfile(): Observable<Profile>

    fun observeProfileById(id: Profile.Id): Observable<Profile>

    fun followProfileById(id: Profile.Id): Completable

    fun unfollowProfileById(id: Profile.Id): Completable

    fun getFollowersById(id: Profile.Id): Single<List<Profile.Header>>

    fun getSubscriptionsById(id: Profile.Id): Single<List<Profile.Header>>

    fun searchProfiles(query: String, limit: Int): Single<List<Profile.Header>>
}