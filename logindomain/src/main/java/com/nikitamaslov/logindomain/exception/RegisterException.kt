package com.nikitamaslov.logindomain.exception

sealed class RegisterException(message: String? = null) : Exception(message) {

    companion object Factory {

        fun invalidUserData(message: String? = null) = InvalidUserDataException(message)

        fun credentialTokenAlreadyExists(message: String? = null) =
            CredentialTokenAlreadyExistsException(message)

        fun invalidCredentialToken(message: String? = null) =
            InvalidCredentialTokenException(message)

        fun invalidCredentialKey(message: String? = null) = InvalidCredentialKeyException(message)
    }
}

class InvalidUserDataException(message: String? = null) : RegisterException(message)

class CredentialTokenAlreadyExistsException(message: String? = null) : RegisterException(message)

class InvalidCredentialTokenException(message: String? = null) : RegisterException(message)

class InvalidCredentialKeyException(message: String? = null) : RegisterException(message)
