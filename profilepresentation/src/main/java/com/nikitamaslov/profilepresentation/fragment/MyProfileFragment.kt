package com.nikitamaslov.profilepresentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.databinding.FragmentMyProfileBinding
import com.nikitamaslov.profilepresentation.viewmodel.MyProfileViewModel
import javax.inject.Inject

class MyProfileFragment : ProfileFragment<MyProfileViewModel, FragmentMyProfileBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutResId: Int = R.layout.fragment_my_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun createViewModel(): MyProfileViewModel = getViewModel(viewModelFactory)

    override fun FragmentMyProfileBinding.init() {
        viewmodel = this@MyProfileFragment.viewModel
    }

    private fun setupToolbar() {
        binding.menuSearch.setOnClickListener {
            val direction = GraphMainDirections.toProfileSearch()
            findNavController().navigate(direction)
        }
    }
}