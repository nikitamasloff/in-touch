package com.nikitamaslov.loginpresentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.navigation.extensions.observeNavigation
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.loginpresentation.databinding.FragmentLoginBinding
import com.nikitamaslov.loginpresentation.viewmodel.LoginViewModel
import javax.inject.Inject

class LoginFragment : InjectingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginViewModel = getViewModel(viewModelFactory)
        binding.viewmodel = this.loginViewModel
        observeNavigationEvents()
    }

    private fun observeNavigationEvents() {
        observeNavigation(loginViewModel.navigator)
    }
}
