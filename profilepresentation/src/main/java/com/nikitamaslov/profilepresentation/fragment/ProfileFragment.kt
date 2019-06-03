package com.nikitamaslov.profilepresentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.navigation.extensions.observeNavigation
import com.nikitamaslov.profilepresentation.viewmodel.ProfileViewModel

abstract class ProfileFragment<VM : ProfileViewModel, DB : ViewDataBinding> : InjectingFragment() {

    protected lateinit var viewModel: VM

    protected lateinit var binding: DB

    private var progressDialog: KProgressHUD? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layoutResId,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewModel = createViewModel()
        this.binding.init()
        initProgressDialog(requireContext())
        observeErrors()
        observeLoading()
        observeNavigation(viewModel.navigator)
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
                Snackbar.make(binding.root, resId, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract fun createViewModel(): VM

    protected abstract fun DB.init()
}