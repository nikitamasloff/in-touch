package com.nikitamaslov.logindomain.model

data class Credential(val token: Token, val key: Key) {

    data class Token(val email: String)
    data class Key(val password: String)
}
