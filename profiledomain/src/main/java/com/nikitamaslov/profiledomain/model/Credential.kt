package com.nikitamaslov.profiledomain.model

class Credential private constructor() {

    data class Token(val email: String)
    data class Key(val password: String)
}
