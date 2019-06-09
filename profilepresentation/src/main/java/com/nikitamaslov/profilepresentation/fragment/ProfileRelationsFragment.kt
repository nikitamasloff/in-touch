package com.nikitamaslov.profilepresentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.navigation.extensions.observeNavigation
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.adapter.OnProfileSelectListener
import com.nikitamaslov.profilepresentation.adapter.ProfileHeaderAdapter
import com.nikitamaslov.profilepresentation.viewmodel.ProfileRelationsViewModel
import kotlinx.android.synthetic.main.fragment_profile_relations.*
import javax.inject.Inject

class ProfileRelationsFragment : InjectingFragment() {

    enum class Mode(
        val id: Int,
        @StringRes val titleResId: Int
    ) {
        FOLLOWERS(0, R.string.relations_followers),
        SUBSCRIPTIONS(1, R.string.relations_subscriptions);

        companion object {
            fun ofId(id: Int) = values().first { it.id == id }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileRelationsViewModel

    private val args: ProfileRelationsFragmentArgs by navArgs()

    private lateinit var mode: Mode

    private lateinit var adapter: ProfileHeaderAdapter

    private var progressDialog: KProgressHUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = Mode.ofId(args.mode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_profile_relations,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewModel = createViewModel()
        initProgressDialog(requireContext())
        setupList()
        setupTitle()
        setupToolbar()
        observeLoading()
        observeErrors()
        observeNavigation(viewModel.navigator)
        requestAndObserveProfiles()
    }

    private fun createViewModel(): ProfileRelationsViewModel =
        getViewModel<ProfileRelationsViewModel>(viewModelFactory)
            .apply { initProfileId(args.profileId) }

    private fun setupList() {
        val adapter = ProfileHeaderAdapter(
            callback = OnProfileSelectListener(viewModel::showProfile)
        )
        recycler_view.adapter = adapter
        this.adapter = adapter
    }

    private fun setupTitle() {
        tv_title.setText(mode.titleResId)
    }

    private fun initProgressDialog(context: Context) {
        this.progressDialog = KProgressHUD.create(context)
            .setAnimationSpeed(1)
            .setAutoDismiss(false)
            .setCancellable(false)
            .setDimAmount(0.6F)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
    }

    private fun requestAndObserveProfiles() {
        when (mode) {
            Mode.FOLLOWERS -> {
                viewModel.queryFollowers()
                observeFollowers()
            }
            Mode.SUBSCRIPTIONS -> {
                viewModel.querySubscriptions()
                observeSubscriptions()
            }
        }
    }

    private fun observeFollowers() {
        viewModel.followers.observe(viewLifecycleOwner, Observer { profiles ->
            adapter.setProfiles(profiles)
        })
    }

    private fun observeSubscriptions() {
        viewModel.subscriptions.observe(viewLifecycleOwner, Observer { profiles ->
            adapter.setProfiles(profiles)
        })
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading: Boolean? ->
            if (isLoading == true) progressDialog?.takeIf { !it.isShowing }?.show()
            else progressDialog?.takeIf { it.isShowing }?.dismiss()
        })
    }

    private fun observeErrors() {
        viewModel.errorResId.observe(viewLifecycleOwner, Observer { event: StringResEvent? ->
            event?.getResIdIfNotHandled()?.let { resId ->
                Snackbar.make(requireView(), resId, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupToolbar() {
        menu_home.setOnClickListener {
            val direction = GraphMainDirections.toMyProfile()
            findNavController().navigate(direction)
        }
        menu_search.setOnClickListener {
            val direction = GraphMainDirections.toProfileSearch()
            findNavController().navigate(direction)
        }
    }
}
