package com.nikitamaslov.repository.database.posts.mapper

import com.nikitamaslov.postdomain.model.Avatar
import com.nikitamaslov.repository.database.posts.model.DatabaseAvatar

internal fun DatabaseAvatar.mapToAvatar(url: String) =
    Avatar(
        profileId = id
    )