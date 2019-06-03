package com.nikitamaslov.profilepresentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onShow
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.nikitamaslov.core.GraphMainDirections
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.lifecycle.model.StringResEvent
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.profiledomain.model.Profile
import com.nikitamaslov.profilepresentation.R
import com.nikitamaslov.profilepresentation.databinding.FragmentEditProfileBinding
import com.nikitamaslov.profilepresentation.viewmodel.ProfileEditViewModel
import javax.inject.Inject

class ProfileEditFragment : InjectingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileEditViewModel

    private lateinit var binding: FragmentEditProfileBinding

    private var progressDialog: KProgressHUD? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_edit_profile,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewModel = createViewModel()
        this.binding.init()
        observeErrors()
        observeLoading()
        observeSummaries()
        initProgressDialog(requireContext())
        registerInputListeners()
        setupToolbar()
    }

    private fun createViewModel(): ProfileEditViewModel = getViewModel(viewModelFactory)

    private fun FragmentEditProfileBinding.init() {
        viewmodel = this@ProfileEditFragment.viewModel
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

    private fun observeSummaries() {
        viewModel.myProfile.observe(viewLifecycleOwner, Observer { profile: Profile? ->
            val initialsSummary = profile?.initials?.let {
                getString(R.string.template_initials, it.firstName, it.lastName)
            } ?: ""
            val descriptionSummary = profile?.description?.src ?: ""

            binding.mpInitials.setSummary(initialsSummary)
            binding.mpDescription.setSummary(descriptionSummary)
        })
    }

    private fun registerInputListeners() {
        binding.mpInitials.setOnClickListener {
            val initials = viewModel.myProfile.value?.initials
            inputInitials(
                initialFirstName = initials?.firstName ?: "",
                initialLastName = initials?.lastName ?: ""
            )
        }
        binding.mpDescription.setOnClickListener {
            inputDescription(initialDescription = viewModel.myProfile.value?.description?.src ?: "")
        }
        binding.mpEmail.setOnClickListener { inputEmail() }
        binding.mpPassword.setOnClickListener { inputPassword() }
    }

    private fun inputInitials(initialFirstName: String, initialLastName: String) {
        MaterialDialog(requireContext())
            .title(res = R.string.action_change_initials)
            .icon(R.drawable.ic_initials_black_24dp)
            .customView(R.layout.dialog_change_initials)
            .onShow { dialog ->
                val customView = dialog.getCustomView()
                val firstName = customView.findViewById<EditText>(R.id.et_first_name)
                val lastName = customView.findViewById<EditText>(R.id.et_last_name)

                firstName.setText(initialFirstName)
                lastName.setText(initialLastName)
            }
            .positiveButton(res = R.string.action_confirm) { dialog ->
                val customView = dialog.getCustomView()
                val firstName = customView.findViewById<EditText>(R.id.et_first_name)
                    .text?.takeIf { it.isNotEmpty() }?.toString()
                val lastName = customView.findViewById<EditText>(R.id.et_last_name)
                    .text?.takeIf { it.isNotEmpty() }?.toString()

                if (firstName == null || lastName == null) {
                    toast(R.string.warning_empty_field)
                } else {
                    viewModel.changeInitials(firstName, lastName)
                    dialog.dismiss()
                }
            }
            .negativeButton(res = R.string.action_cancel) { it.dismiss() }
            .cancelable(true)
            .noAutoDismiss()
            .show()
    }

    private fun inputDescription(initialDescription: String) {
        MaterialDialog(requireContext())
            .title(res = R.string.action_change_description)
            .icon(R.drawable.ic_description_black_24dp)
            .customView(R.layout.dialog_change_description)
            .onShow { dialog ->
                val description = dialog.getCustomView().findViewById<EditText>(R.id.et_description)
                description.setText(initialDescription)
            }
            .positiveButton(res = R.string.action_confirm) { dialog ->
                val description = dialog.getCustomView().findViewById<EditText>(R.id.et_description)
                    .text?.takeIf { it.isNotEmpty() }?.toString()

                if (description == null) {
                    toast(R.string.warning_empty_field)
                } else {
                    viewModel.changeDescription(description)
                    dialog.dismiss()
                }
            }
            .negativeButton(res = R.string.action_cancel) { it.dismiss() }
            .cancelable(true)
            .noAutoDismiss()
            .show()
    }

    private fun inputEmail() {
        MaterialDialog(requireContext())
            .title(res = R.string.action_change_email)
            .icon(R.drawable.ic_email_black_24dp)
            .customView(R.layout.dialog_change_email)
            .positiveButton(res = R.string.action_confirm) { dialog ->
                val customView = dialog.getCustomView()
                val newEmail = customView.findViewById<EditText>(R.id.et_email)
                    .text?.takeIf { it.isNotEmpty() }?.toString()
                val password = customView.findViewById<EditText>(R.id.et_password)
                    .text?.takeIf { it.isNotEmpty() }?.toString()

                if (newEmail == null || password == null) {
                    toast(R.string.warning_empty_field)
                } else {
                    viewModel.changeEmail(newEmail, password)
                    dialog.dismiss()
                }
            }
            .negativeButton(res = R.string.action_cancel) { it.dismiss() }
            .cancelable(true)
            .noAutoDismiss()
            .show()
    }

    private fun inputPassword() {
        MaterialDialog(requireContext())
            .title(res = R.string.action_change_password)
            .icon(R.drawable.ic_password_black_24dp)
            .customView(R.layout.dialog_change_password)
            .positiveButton(res = R.string.action_confirm) { dialog ->
                val customView = dialog.getCustomView()
                val currentPassword = customView.findViewById<EditText>(R.id.et_cur_password)
                    .text?.takeIf { it.isNotEmpty() }?.toString()
                val newPassword = customView.findViewById<EditText>(R.id.et_new_password)
                    .text?.takeIf { it.isNotEmpty() }?.toString()

                if (currentPassword == null || newPassword == null) {
                    toast(R.string.warning_empty_field)
                } else {
                    viewModel.changePassword(currentPassword, newPassword)
                    dialog.dismiss()
                }
            }
            .negativeButton(res = R.string.action_cancel) { it.dismiss() }
            .cancelable(true)
            .noAutoDismiss()
            .show()
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

    private fun toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), text, duration).show()
    }

    private fun toast(@StringRes textResId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), textResId, duration).show()
    }
}