package com.nikitamaslov.repository.database.posts.mapper

import com.nikitamaslov.postdomain.exception.SystemException
import com.nikitamaslov.repository.database.exception.DatabaseNetworkConnectionException
import com.nikitamaslov.repository.database.exception.DatabaseServerException

internal fun mapDatabaseToPostSystemException(t: Throwable): Throwable =
    when (t) {
        is DatabaseNetworkConnectionException -> SystemException.networkConnection()
        is DatabaseServerException -> SystemException.server()
        else -> t
    }