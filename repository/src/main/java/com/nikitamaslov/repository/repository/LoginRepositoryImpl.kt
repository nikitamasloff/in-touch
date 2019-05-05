package com.nikitamaslov.repository.repository

import com.nikitamaslov.core.rx.extensions.mapError
import com.nikitamaslov.logindomain.model.Credential
import com.nikitamaslov.logindomain.model.User
import com.nikitamaslov.logindomain.repository.LoginRepository
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.exception.AuthNotLoggedInException
import com.nikitamaslov.repository.auth.mapper.*
import com.nikitamaslov.repository.database.users.UserDatabase
import com.nikitamaslov.repository.database.users.mapper.mapDatabaseUserToRegisterException
import com.nikitamaslov.repository.database.users.mapper.mapToDatabaseUserCreator
import com.nikitamaslov.repository.database.users.model.DatabaseUserCreator
import io.reactivex.Completable

class LoginRepositoryImpl(
    private val auth: Auth,
    private val userDatabase: UserDatabase
) : LoginRepository {

    override fun logIn(credential: Credential): Completable =
        auth.logIn(credential = credential.mapToAuthCredential())
            .mapError(::mapAuthToLoginException)

    override fun restore(token: Credential.Token): Completable =
        auth.restore(credentialToken = token.mapToAuthCredentialToken())
            .mapError(::mapAuthToRestoreException)

    override fun register(user: User, credential: Credential): Completable =
        auth.register(credential = credential.mapToAuthCredential())
            .andThen(createUserInDatabase(creator = user.mapToDatabaseUserCreator()))
            .mapError(::mapAuthToRegisterException)
            .mapError(::mapDatabaseUserToRegisterException)

    private fun createUserInDatabase(creator: DatabaseUserCreator): Completable =
        when (val id = auth.currentUser?.id) {
            null -> Completable.error(AuthNotLoggedInException())
            else -> userDatabase.createUser(id, creator)
        }
}
