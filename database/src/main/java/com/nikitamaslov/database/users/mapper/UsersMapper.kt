package com.nikitamaslov.database.users.mapper

import com.nikitamaslov.database.users.model.UserFields
import com.nikitamaslov.repository.database.users.model.DatabaseUserCreator

internal fun DatabaseUserCreator.asFieldsMap(): Map<String, Any> =
    mapOf(
        UserFields.FIRST_NAME to this.firstName,
        UserFields.LAST_NAME to this.lastName
    )
