package com.nikitamaslov.repository.auth.model

data class AuthCredential(val token: Token, val key: Key) {

    data class Token(val email: String)
    data class Key(val password: String)
}
