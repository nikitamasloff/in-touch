package com.nikitamaslov.logindomain.exception

sealed class RestoreException(message: String? = null) : Exception(message) {

    companion object Factory {

        fun noSuchCredentialToken(message: String? = null) = NoSuchCredentialTokenException(message)
    }
}

class NoSuchCredentialTokenException(message: String? = null) : RestoreException(message)
