package com.nikitamaslov.profilepresentation.adapter

import com.nikitamaslov.profiledomain.model.Profile

interface OnProfileSelectListener {

    fun onSelect(profile: Profile.Header)
}

fun OnProfileSelectListener(block: (profile: Profile.Header) -> Unit) =
    object : OnProfileSelectListener {

        override fun onSelect(profile: Profile.Header) {
            block(profile)
        }
    }