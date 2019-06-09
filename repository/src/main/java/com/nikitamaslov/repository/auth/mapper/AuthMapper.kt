package com.nikitamaslov.repository.auth.mapper

import com.nikitamaslov.repository.auth.model.AuthCredential
import com.nikitamaslov.logindomain.model.Credential as LoginCredential
import com.nikitamaslov.profiledomain.model.Credential as ProfileCredential

internal fun LoginCredential.Token.mapToAuthCredentialToken() =
    AuthCredential.Token(email)

internal fun LoginCredential.Key.mapToAuthCredentialKey() =
    AuthCredential.Key(password)

internal fun LoginCredential.mapToAuthCredential() =
    AuthCredential(
        token = token.mapToAuthCredentialToken(),
        key = key.mapToAuthCredentialKey()
    )

internal fun ProfileCredential.Token.mapToAuthCredentialToken() =
    AuthCredential.Token(email)

internal fun ProfileCredential.Key.mapToAuthCredentialKey() =
    AuthCredential.Key(password)
