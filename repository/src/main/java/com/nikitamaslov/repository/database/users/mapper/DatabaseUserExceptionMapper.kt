package com.nikitamaslov.repository.database.users.mapper

import com.nikitamaslov.repository.storage.exception.StorageNetworkConnectionException
import com.nikitamaslov.repository.storage.exception.StorageServerException
import com.nikitamaslov.logindomain.exception.SystemException as LoginSystemException
import com.nikitamaslov.profiledomain.exception.SystemException as ProfileSystemException

internal fun mapDatabaseToLoginSystemException(t: Throwable): Throwable =
    when (t) {
        is StorageNetworkConnectionException -> LoginSystemException.networkConnection()
        is StorageServerException -> LoginSystemException.server()
        else -> t
    }

internal fun mapDatabaseToProfileSystemException(t: Throwable): Throwable =
    when (t) {
        is StorageNetworkConnectionException -> ProfileSystemException.networkConnection()
        is StorageServerException -> ProfileSystemException.server()
        else -> t
    }