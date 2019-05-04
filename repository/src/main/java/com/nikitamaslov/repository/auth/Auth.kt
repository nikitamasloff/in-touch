package com.nikitamaslov.repository.auth

import com.nikitamaslov.repository.auth.model.AuthUser

interface Auth {

    val currentUser: AuthUser?
}
