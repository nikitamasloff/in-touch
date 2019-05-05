package com.nikitamaslov.loginpresentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.loginpresentation.databinding.FragmentRestoreBinding
import com.nikitamaslov.loginpresentation.viewmodel.RestoreViewModel
import javax.inject.Inject

class RestoreFragment : InjectingFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var restoreViewModel: RestoreViewModel

    private lateinit var binding: FragmentRestoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestoreBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        restoreViewModel = getViewModel(viewModelFactory)
        binding.viewmodel = this.restoreViewModel
    }
}
