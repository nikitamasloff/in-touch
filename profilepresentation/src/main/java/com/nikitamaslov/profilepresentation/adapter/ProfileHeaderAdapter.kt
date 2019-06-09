package com.nikitamaslov.profilepresentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.databinding.ItemProfileHeaderBinding

class ProfileHeaderAdapter(
    private var profiles: List<Profile.Header>,
    private val callback: OnProfileSelectListener
) : RecyclerView.Adapter<ProfileHeaderViewHolder>() {

    constructor(callback: OnProfileSelectListener) : this(emptyList(), callback)

    fun setProfiles(profiles: List<Profile.Header>) {
        this.profiles = profiles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = profiles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHeaderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileHeaderBinding.inflate(inflater)
        return ProfileHeaderViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: ProfileHeaderViewHolder, position: Int) {
        holder.setProfile(profiles[position])
    }
}