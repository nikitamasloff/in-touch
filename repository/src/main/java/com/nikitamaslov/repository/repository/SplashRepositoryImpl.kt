package com.nikitamaslov.repository.repository

import com.nikitamaslov.repository.auth.Auth
import com.nikitamaslov.splashdomain.repository.SplashRepository

class SplashRepositoryImpl(private val auth: Auth) : SplashRepository {

    override fun isLoggedIn(): Boolean = auth.currentUser != null
}
