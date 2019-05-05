package com.nikitamaslov.splashpresentation.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.nikitamaslov.core.fragment.InjectingFragment
import com.nikitamaslov.core.navigation.extensions.observeNavigation
import com.nikitamaslov.core.viewmodel.extensions.getViewModel
import com.nikitamaslov.splashpresentation.databinding.FragmentSplashBinding
import com.nikitamaslov.splashpresentation.viewmodel.SplashViewModel
import javax.inject.Inject

private const val STARTUP_DURATION = 2000L

class SplashFragment : InjectingFragment() {

    @Inject
    lateinit var handler: Handler

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val splashViewModel by lazy<SplashViewModel> { getViewModel(viewModelFactory) }

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewmodel = this.splashViewModel
        postDelayed(STARTUP_DURATION, ::observeNavigationEvents)
    }

    private fun observeNavigationEvents() {
        observeNavigation(splashViewModel.navigator)
    }

    private fun postDelayed(delayMillis: Long, action: () -> Unit) {
        handler.postDelayed(action, delayMillis)
    }
}
