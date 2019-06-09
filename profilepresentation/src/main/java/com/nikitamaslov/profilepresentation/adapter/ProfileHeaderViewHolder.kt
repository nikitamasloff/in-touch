package com.nikitamaslov.profilepresentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.databinding.ItemProfileHeaderBinding

class ProfileHeaderViewHolder(
    private val binding: ItemProfileHeaderBinding,
    listener: OnProfileSelectListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    fun setProfile(profile: Profile.Header) {
        binding.profile = profile
    }
}