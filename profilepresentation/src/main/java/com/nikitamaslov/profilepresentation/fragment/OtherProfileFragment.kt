package com.nikitamaslov.profilepresentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.databinding.FragmentOtherProfileBinding
import com.nikitamaslov.profilepresentation.viewmodel.OtherProfileViewModel
import javax.inject.Inject

class OtherProfileFragment : ProfileFragment<OtherProfileViewModel, FragmentOtherProfileBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layoutResId: Int = R.layout.fragment_other_profile

    private val args: OtherProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    override fun createViewModel(): OtherProfileViewModel =
        getViewModel<OtherProfileViewModel>(viewModelFactory)
            .apply { initProfileId(args.profileId) }

    override fun FragmentOtherProfileBinding.init() {
        viewmodel = this@OtherProfileFragment.viewModel
    }

    private fun setupToolbar() {
        binding.menuHome.setOnClickListener {
            val direction = GraphMainDirections.toMyProfile()
            findNavController().navigate(direction)
        }
        binding.menuSearch.setOnClickListener {
            val direction = GraphMainDirections.toProfileSearch()
            findNavController().navigate(direction)
        }
    }
}