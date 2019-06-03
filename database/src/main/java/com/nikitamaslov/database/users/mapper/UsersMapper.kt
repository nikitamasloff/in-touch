package com.nikitamaslov.database.users.mapper

import com.nikitamaslov.database.users.model.UserFields
import com.nikitamaslov.repository.database.users.model.DatabaseUser

internal fun DatabaseUser.Creator.asFieldsMap(id: String): Map<String, Any> =
    mapOf(
        UserFields.ID to id,
        UserFields.FIRST_NAME to this.firstName,
        UserFields.LAST_NAME to this.lastName
    )

internal fun DatabaseUser.Initials.asFieldsMap(): Map<String, Any> =
    mapOf(
        UserFields.FIRST_NAME to this.firstName,
        UserFields.LAST_NAME to this.lastName
    )

internal fun DatabaseUser.Description.asFieldsMap(): Map<String, Any> =
    mapOf(
        UserFields.DESCRIPTION to this.src
    )

internal fun DatabaseUser.Header.asFieldsMap(): Map<String, Any> =
    mapOf(
        UserFields.FIRST_NAME to this.initials.firstName,
        UserFields.LAST_NAME to this.initials.lastName
    )

internal fun Map<String, Any>.asDatabaseUserHeader(id: String) =
    DatabaseUser.Header(
        id = id,
        initials = DatabaseUser.Initials(
            firstName = getOrDefault(UserFields.FIRST_NAME, default = ""),
            lastName = getOrDefault(UserFields.LAST_NAME, default = "")
        )
    )

internal fun Map<String, Any>.asDatabaseUser(id: String) =
    DatabaseUser(
        id = id,
        initials = DatabaseUser.Initials(
            firstName = getOrDefault(UserFields.FIRST_NAME, default = ""),
            lastName = getOrDefault(UserFields.LAST_NAME, default = "")
        ),
        description = DatabaseUser.Description(
            src = getOrDefault(UserFields.DESCRIPTION, default = "")
        ),
        followers = getOrDefault<Map<String, Map<String, Any>>>(
            UserFields.FOLLOWERS,
            default = emptyMap()
        ).map { it.value.asDatabaseUserHeader(it.key) },
        subscriptions = getOrDefault<Map<String, Map<String, Any>>>(
            UserFields.SUBSCRIPTIONS,
            default = emptyMap()
        ).map { it.value.asDatabaseUserHeader(it.key) }
    )

@Suppress("UNCHECKED_CAST")
private fun <T> Map<String, Any>.getOrDefault(key: String, default: T): T =
    get(key) as T? ?: default
