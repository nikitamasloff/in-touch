package com.nikitamaslov.postdomain.model

import com.nikitamaslov.profiledomain.model.Profile

data class Avatar(
    val profileId: Profile.Id,
    val url: Url
) {

    data class Creator(val filePath: String)
}