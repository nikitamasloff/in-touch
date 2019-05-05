package com.nikitamaslov.logindomain.exception

sealed class SystemException(message: String? = null) : Exception(message) {

    companion object Factory {

        fun server() = ServerException()
        fun networkConnection() = NetworkConnectionException()
    }
}

class ServerException(message: String? = null) : SystemException(message)

class NetworkConnectionException(message: String? = null) : SystemException(message)
