package com.nikitamaslov.logindomain.exception

sealed class LogInException(message: String? = null) : Exception(message) {

    companion object Factory {

        fun wrongToken(message: String? = null) = WrongTokenException(message)
        fun wrongKey(message: String? = null) = WrongKeyException(message)
    }
}

class WrongTokenException(message: String? = null) : LogInException(message)

class WrongKeyException(message: String? = null) : LogInException(message)
