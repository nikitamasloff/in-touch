package com.nikitamaslov.repository.auth.mapper

import com.nikitamaslov.logindomain.exception.LogInException
import com.nikitamaslov.logindomain.exception.RegisterException
import com.nikitamaslov.logindomain.exception.RestoreException
import com.nikitamaslov.logindomain.exception.SystemException
import com.nikitamaslov.repository.auth.exception.*

internal fun mapAuthToLoginException(t: Throwable): Throwable =
    when (t) {
        is AuthWrongCredentialTokenException -> LogInException.wrongToken()
        is AuthWrongCredentialKeyException -> LogInException.wrongKey()
        is AuthNetworkConnectionException -> SystemException.networkConnection()
        is AuthServerException -> SystemException.server()
        else -> t
    }

internal fun mapAuthToRegisterException(t: Throwable): Throwable =
    when (t) {
        is AuthInvalidCredentialTokenException -> RegisterException.invalidCredentialToken()
        is AuthInvalidCredentialKeyException -> RegisterException.invalidCredentialKey()
        is AuthCredentialCollisionException -> RegisterException.credentialTokenAlreadyExists()
        is AuthNotLoggedInException -> SystemException.server()
        is AuthNetworkConnectionException -> SystemException.networkConnection()
        is AuthServerException -> SystemException.server()
        else -> t
    }

internal fun mapAuthToRestoreException(t: Throwable): Throwable =
    when (t) {
        is AuthInvalidCredentialTokenException -> RestoreException.noSuchCredentialToken()
        is AuthNetworkConnectionException -> SystemException.networkConnection()
        is AuthServerException -> SystemException.server()
        else -> t
    }
