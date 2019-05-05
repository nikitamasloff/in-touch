package com.nikitamaslov.auth

import com.google.firebase.auth.FirebaseAuth
import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.repository.auth.model.AuthUser

class AuthImpl(private val auth: FirebaseAuth) : Auth {

    override val currentUser: AuthUser? get() = auth.currentUser?.uid?.let(::AuthUser)
}
