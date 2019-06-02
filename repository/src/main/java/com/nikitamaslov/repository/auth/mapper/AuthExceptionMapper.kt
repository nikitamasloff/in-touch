package com.nikitamaslov.repository.auth.mapper

import com.nikitamaslov.logindomain.exception.LogInException
import com.nikitamaslov.logindomain.exception.RegisterException
import com.nikitamaslov.logindomain.exception.RestoreException
import com.nikitamaslov.profiledomain.exception.InvalidNewCredentialKeyException
import com.nikitamaslov.profiledomain.exception.InvalidNewCredentialTokenException
import com.nikitamaslov.profiledomain.exception.NewCredentialTokenCollisionException
import com.nikitamaslov.profiledomain.exception.WrongCurrentCredentialKeyException
import com.nikitamaslov.repository.auth.exception.*
import com.nikitamaslov.logindomain.exception.SystemException as LoginSystemException
import com.nikitamaslov.profiledomain.exception.SystemException as ProfileSystemException

internal fun mapAuthToLoginException(t: Throwable): Throwable =
    when (t) {
        is AuthWrongCredentialTokenException -> LogInException.wrongToken()
        is AuthWrongCredentialKeyException -> LogInException.wrongKey()
        is AuthNetworkConnectionException -> LoginSystemException.networkConnection()
        is AuthServerException -> LoginSystemException.server()
        else -> t
    }

internal fun mapAuthToRegisterException(t: Throwable): Throwable =
    when (t) {
        is AuthInvalidCredentialTokenException -> RegisterException.invalidCredentialToken()
        is AuthInvalidCredentialKeyException -> RegisterException.invalidCredentialKey()
        is AuthCredentialCollisionException -> RegisterException.credentialTokenAlreadyExists()
        is AuthNotLoggedInException -> LoginSystemException.server()
        is AuthNetworkConnectionException -> LoginSystemException.networkConnection()
        is AuthServerException -> LoginSystemException.server()
        else -> t
    }

internal fun mapAuthToRestoreException(t: Throwable): Throwable =
    when (t) {
        is AuthInvalidCredentialTokenException -> RestoreException.noSuchCredentialToken()
        is AuthNetworkConnectionException -> LoginSystemException.networkConnection()
        is AuthServerException -> LoginSystemException.server()
        else -> t
    }

internal fun mapAuthToProfileCredentialException(t: Throwable): Throwable =
    when (t) {
        is AuthWrongCredentialKeyException -> WrongCurrentCredentialKeyException(t.message)
        is AuthWrongCredentialTokenException -> WrongCurrentCredentialKeyException(t.message)
        is AuthInvalidCredentialTokenException -> InvalidNewCredentialTokenException(t.message)
        is AuthInvalidCredentialKeyException -> InvalidNewCredentialKeyException(t.message)
        is AuthCredentialCollisionException -> NewCredentialTokenCollisionException(t.message)
        is AuthNetworkConnectionException -> ProfileSystemException.networkConnection()
        is AuthServerException -> ProfileSystemException.server()
        else -> t
    }
