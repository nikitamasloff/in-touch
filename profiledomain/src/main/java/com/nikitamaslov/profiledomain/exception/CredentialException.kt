package com.nikitamaslov.profiledomain.exception

sealed class CredentialException(message: String? = null) : Exception(message) {

    companion object Factory {

        fun wrongCurrentCredentialKey(message: String? = null) =
            WrongCurrentCredentialKeyException(message)

        fun invalidNewCredentialKey(message: String? = null) =
            InvalidNewCredentialKeyException(message)

        fun invalidNewCredentialToken(message: String? = null) =
            InvalidNewCredentialTokenException(message)

        fun newCredentialTokenCollision(message: String? = null) =
            NewCredentialTokenCollisionException(message)
    }
}

class WrongCurrentCredentialKeyException(message: String? = null) : CredentialException(message)

class InvalidNewCredentialKeyException(message: String? = null) : CredentialException(message)

class InvalidNewCredentialTokenException(message: String? = null) : CredentialException(message)

class NewCredentialTokenCollisionException(message: String? = null) : CredentialException(message)