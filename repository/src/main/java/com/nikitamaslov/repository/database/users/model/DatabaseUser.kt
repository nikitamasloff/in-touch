package com.nikitamaslov.repository.database.users.model

data class DatabaseUser(
    val id: String,
    val initials: Initials,
    val description: Description,
    val followers: List<Header>,
    val subscriptions: List<Header>
) {

    data class Initials(
        val firstName: String,
        val lastName: String
    )

    data class Description(val src: String)

    data class Header(
        val id: String,
        val initials: Initials
    )

    data class Creator(
        val firstName: String,
        val lastName: String
    )
}