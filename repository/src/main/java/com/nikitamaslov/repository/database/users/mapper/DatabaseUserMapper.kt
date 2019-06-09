package com.nikitamaslov.repository.database.users.mapper

import com.nikitamaslov.logindomain.model.User
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.repository.database.users.model.DatabaseUser

internal fun User.mapToDatabaseUserCreator() = DatabaseUser.Creator(firstName, lastName)

internal fun Profile.Initials.mapToDatabaseUserInitials() =
    DatabaseUser.Initials(firstName, lastName)

internal fun DatabaseUser.Initials.mapToProfileInitials() = Profile.Initials(firstName, lastName)

internal fun Profile.Description.mapToDatabaseUserDescription() = DatabaseUser.Description(src)

internal fun DatabaseUser.Description.mapToProfileDescription() = Profile.Description(src)

internal fun DatabaseUser.mapToProfile(currentUserId: String) =
    Profile(
        id = Profile.Id(id),
        description = description.mapToProfileDescription(),
        initials = initials.mapToProfileInitials(),
        isMe = currentUserId == id,
        followersAmount = followers.size,
        subscriptionsAmount = subscriptions.size,
        relation = Profile.Relation(
            isFollower = subscriptions.find { it.id == currentUserId } != null,
            isSubscription = followers.find { it.id == currentUserId } != null
        ).takeIf { currentUserId != id }
    )

internal fun DatabaseUser.Header.mapToProfileHeader(currentUserId: String) =
    Profile.Header(
        id = Profile.Id(src = id),
        initials = initials.mapToProfileInitials(),
        isMe = currentUserId == id
    )
