package com.nikitamaslov.loginpresentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.loginpresentation.databinding.FragmentRegisterBinding
import com.nikitamaslov.loginpresentation.viewmodel.RegisterViewModel
import javax.inject.Inject

class RegisterFragment : InjectingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerViewModel = getViewModel(viewModelFactory)
        binding.viewmodel = this.registerViewModel
    }
}
