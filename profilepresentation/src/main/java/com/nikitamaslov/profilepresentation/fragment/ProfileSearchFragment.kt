package com.nikitamaslov.profilepresentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import com.nikitamaslov.profilepresentation.viewmodel.ProfileSearchViewModel
import kotlinx.android.synthetic.main.fragment_profile_search.*
import javax.inject.Inject

class ProfileSearchFragment : InjectingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileSearchViewModel

    private lateinit var adapter: ProfileHeaderAdapter

    private var progressDialog: KProgressHUD? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_profile_search,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewModel = createViewModel()
        initProgressDialog(requireContext())
        setSearchListener()
        setupList()
        observeFoundedProfiles()
        observeLoading()
        observeErrors()
        observeNavigation(viewModel.navigator)
        setupToolbar()
    }

    private fun createViewModel(): ProfileSearchViewModel = getViewModel(viewModelFactory)

    private fun setSearchListener() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchProfiles(query)
            }
        })
    }

    private fun setupList() {
        val adapter = ProfileHeaderAdapter(
            callback = OnProfileSelectListener(viewModel::showProfile)
        )
        this.adapter = adapter
        recycler_view.adapter = adapter
    }

    private fun initProgressDialog(context: Context) {
        this.progressDialog = KProgressHUD.create(context)
            .setAnimationSpeed(1)
            .setAutoDismiss(false)
            .setCancellable(false)
            .setDimAmount(0.6F)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
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

    private fun observeFoundedProfiles() {
        viewModel.foundedProfiles.observe(viewLifecycleOwner, Observer { profiles ->
            adapter.setProfiles(profiles)
        })
    }

    private fun setupToolbar() {
        menu_home.setOnClickListener {
            val direction = GraphMainDirections.toMyProfile()
            findNavController().navigate(direction)
        }
    }
}
