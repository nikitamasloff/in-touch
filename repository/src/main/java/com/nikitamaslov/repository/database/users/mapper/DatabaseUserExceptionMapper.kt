package com.nikitamaslov.repository.database.users.mapper

import com.nikitamaslov.repository.database.exception.DatabaseNetworkConnectionException
import com.nikitamaslov.repository.database.exception.DatabaseServerException
import com.nikitamaslov.logindomain.exception.SystemException as LoginSystemException
import com.nikitamaslov.profiledomain.exception.SystemException as ProfileSystemException

internal fun mapDatabaseToLoginSystemException(t: Throwable): Throwable =
    when (t) {
        is DatabaseNetworkConnectionException -> LoginSystemException.networkConnection()
        is DatabaseServerException -> LoginSystemException.server()
        else -> t
    }

internal fun mapDatabaseToProfileSystemException(t: Throwable): Throwable =
    when (t) {
        is DatabaseNetworkConnectionException -> ProfileSystemException.networkConnection()
        is DatabaseServerException -> ProfileSystemException.server()
        else -> t
    }