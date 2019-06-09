package com.nikitamaslov.auth

import com.google.firebase.auth.*
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.rx.extensions.safeOnComplete
import com.nikitamaslov.core.rx.extensions.safeOnError
import com.nikitamaslov.core.rx.extensions.safeOnSuccess
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.exception.*
import com.nikitamaslov.repository.auth.model.AuthCredential
import com.nikitamaslov.repository.auth.model.AuthUser
import io.reactivex.Completable
import io.reactivex.Single

class AuthImpl(
    private val auth: FirebaseAuth,
    private val schedulers: RxSchedulerProvider,
    private val networkManager: NetworkManager
) : Auth {

    override val currentUser: AuthUser? get() = auth.currentUser?.uid?.let(::AuthUser)

    override fun register(credential: AuthCredential): Single<String> =
        Single.create<String> { emitter ->

            val email = credential.token.email
            val password = credential.key.password

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    emitter.safeOnSuccess(authResult.user.uid)
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidCredentialsException -> AuthInvalidCredentialTokenException()
                        is FirebaseAuthWeakPasswordException -> AuthInvalidCredentialKeyException()
                        is FirebaseAuthUserCollisionException -> AuthCredentialCollisionException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }

        }
            .subscribeOn(schedulers.io)

    override fun logIn(credential: AuthCredential): Completable =
        Completable.create { emitter ->

            val email = credential.token.email
            val password = credential.key.password

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    emitter.safeOnComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidUserException -> AuthWrongCredentialTokenException()
                        is FirebaseAuthInvalidCredentialsException -> AuthWrongCredentialKeyException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }

        }
            .subscribeOn(schedulers.io)

    override fun restore(credentialToken: AuthCredential.Token): Completable =
        Completable.create { emitter ->

            val email = credentialToken.email

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    emitter.safeOnComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidUserException -> AuthWrongCredentialTokenException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }

        }
            .subscribeOn(schedulers.io)

    override fun changeCredentialToken(
        newToken: AuthCredential.Token,
        key: AuthCredential.Key
    ): Completable {

        val currentPassword = key.password
        val newEmail = newToken.email

        return reauthenticate(currentPassword)
            .andThen(updateEmail(newEmail))
    }

    override fun changeCredentialKey(
        currentKey: AuthCredential.Key,
        newKey: AuthCredential.Key
    ): Completable {

        val currentPassword = currentKey.password
        val newPassword = newKey.password

        return reauthenticate(currentPassword)
            .andThen(updatePassword(newPassword))
    }

    private fun reauthenticate(currentPassword: String): Completable =
        Completable.create { emitter ->

            val currentAuthUser = auth.currentUser
                ?: throw AuthNotLoggedInException()
            val currentEmail = currentAuthUser.email
                ?: throw AuthWrongAuthenticationMethodException()
            val authCredential = EmailAuthProvider.getCredential(currentEmail, currentPassword)

            currentAuthUser.reauthenticate(authCredential)
                .addOnSuccessListener {
                    emitter.safeOnComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidCredentialsException -> AuthInvalidCredentialTokenException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }

        }
            .subscribeOn(schedulers.io)

    private fun updateEmail(newEmail: String): Completable =
        Completable.create { emitter ->

            val currentAuthUser = auth.currentUser
                ?: throw AuthNotLoggedInException()

            currentAuthUser.updateEmail(newEmail)
                .addOnSuccessListener {
                    emitter.safeOnComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidCredentialsException -> AuthInvalidCredentialTokenException()
                        is FirebaseAuthUserCollisionException -> AuthCredentialCollisionException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }

        }
            .subscribeOn(schedulers.io)

    private fun updatePassword(newPassword: String): Completable =
        Completable.create { emitter ->

            val currentAuthUser = auth.currentUser
                ?: throw AuthNotLoggedInException()

            currentAuthUser.updatePassword(newPassword)
                .addOnSuccessListener {
                    emitter.safeOnComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidCredentialsException -> AuthInvalidCredentialTokenException()
                        is FirebaseAuthUserCollisionException -> AuthCredentialCollisionException()
                        else -> systemException()
                    }
                    emitter.safeOnError(error)
                }
        }
            .subscribeOn(schedulers.io)

    override fun logOut(): Completable {
        auth.signOut()
        return Completable.complete()
    }

    private fun systemException() =
        if (!networkManager.isConnected) AuthNetworkConnectionException()
        else AuthServerException()
}
