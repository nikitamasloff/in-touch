package com.nikitamaslov.repository.database.users.mapper

import com.nikitamaslov.logindomain.model.User
import com.nikitamaslov.repository.database.users.model.DatabaseUserCreator

internal fun User.mapToDatabaseUserCreator() =
    DatabaseUserCreator(firstName, lastName)
