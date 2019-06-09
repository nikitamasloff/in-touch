package com.nikitamaslov.repository.database.posts.model

data class DatabaseAvatar(val id: Id) {

    data class Id(val src: String)
}