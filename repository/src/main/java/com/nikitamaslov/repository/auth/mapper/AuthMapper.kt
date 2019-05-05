package com.nikitamaslov.repository.auth.mapper

import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.repository.auth.model.AuthCredential

internal fun Credential.Token.mapToAuthCredentialToken() =
    AuthCredential.Token(email)

internal fun Credential.Key.mapToAuthCredentialKey() =
    AuthCredential.Key(password)

internal fun Credential.mapToAuthCredential() =
    AuthCredential(
        token = token.mapToAuthCredentialToken(),
        key = key.mapToAuthCredentialKey()
    )
