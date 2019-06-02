package com.nikitamaslov.profiledomain.model

data class Profile(
    val id: Id,
    val initials: Initials,
    val description: Description,
    val followersAmount: Int,
    val subscriptionsAmount: Int,
    val isMe: Boolean,
    val relation: Relation?
) {

    data class Id(val src: String)

    data class Initials(
        val firstName: String,
        val lastName: String
    )

    data class Description(val src: String)

    data class Relation(
        val isFollower: Boolean,
        val isSubscription: Boolean
    )

    data class Header(
        val id: Id,
        val initials: Initials,
        val isMe: Boolean
    )
}