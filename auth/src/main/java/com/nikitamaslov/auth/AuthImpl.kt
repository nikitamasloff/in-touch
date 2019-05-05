package com.nikitamaslov.auth

import com.google.firebase.auth.*
import com.nikitamaslov.core.network.NetworkManager
import com.nikitamaslov.core.rx.schedulers.RxSchedulerProvider
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.exception.*
import com.nikitamaslov.repository.auth.model.AuthCredential
import com.nikitamaslov.repository.auth.model.AuthUser
import io.reactivex.Completable

class AuthImpl(
    private val auth: FirebaseAuth,
    private val schedulers: RxSchedulerProvider,
    private val networkManager: NetworkManager
) : Auth {

    override val currentUser: AuthUser? get() = auth.currentUser?.uid?.let(::AuthUser)

    override fun register(credential: AuthCredential): Completable =
        Completable.create { emitter ->

            val email = credential.token.email
            val password = credential.key.password

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidCredentialsException -> AuthInvalidCredentialTokenException()
                        is FirebaseAuthWeakPasswordException -> AuthInvalidCredentialKeyException()
                        is FirebaseAuthUserCollisionException -> AuthCredentialCollisionException()
                        else -> systemException()
                    }
                    emitter.onError(error)
                }

        }
            .subscribeOn(schedulers.io)

    override fun logIn(credential: AuthCredential): Completable =
        Completable.create { emitter ->

            val email = credential.token.email
            val password = credential.key.password

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidUserException -> AuthWrongCredentialTokenException()
                        is FirebaseAuthInvalidCredentialsException -> AuthWrongCredentialKeyException()
                        else -> systemException()
                    }
                    emitter.onError(error)
                }

        }
            .subscribeOn(schedulers.io)

    override fun restore(credentialToken: AuthCredential.Token): Completable =
        Completable.create { emitter ->

            val email = credentialToken.email

            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener { t: Throwable ->
                    val error = when (t) {
                        is FirebaseAuthInvalidUserException -> AuthWrongCredentialTokenException()
                        else -> systemException()
                    }
                    emitter.onError(error)
                }

        }
            .subscribeOn(schedulers.io)

    private fun systemException() =
        if (!networkManager.isConnected) AuthNetworkConnectionException()
        else AuthServerException()
}
