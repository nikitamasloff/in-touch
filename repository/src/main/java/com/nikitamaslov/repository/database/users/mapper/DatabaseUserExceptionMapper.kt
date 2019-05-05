package com.nikitamaslov.repository.database.users.mapper

import com.nikitamaslov.logindomain.exception.RegisterException
import com.nikitamaslov.logindomain.exception.SystemException
import com.nikitamaslov.repository.database.exception.DatabaseNetworkConnectionException
import com.nikitamaslov.repository.database.exception.DatabaseServerException
import com.nikitamaslov.repository.database.users.exception.DatabaseUserCreatorException

internal fun mapDatabaseUserToRegisterException(t: Throwable): Throwable =
    when (t) {
        is DatabaseUserCreatorException -> RegisterException.invalidUserData()
        is DatabaseNetworkConnectionException -> SystemException.networkConnection()
        is DatabaseServerException -> SystemException.server()
        else -> t
    }
