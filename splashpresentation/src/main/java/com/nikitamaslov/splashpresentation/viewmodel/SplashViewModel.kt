package com.nikitamaslov.splashpresentation.viewmodel

import androidx.lifecycle.ViewModel
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.splashdomain.interactor.IsLoggedInUseCase
import com.nikitamaslov.splashpresentation.fragment.SplashFragmentDirections

class SplashViewModel(
    isLoggedIn: IsLoggedInUseCase,
    val navigator: Navigator
) : ViewModel() {

    init {
        if (!isLoggedIn()) navigateToLogin()
        else navigateToMain()
    }

    private fun navigateToLogin() {
        val direction = SplashFragmentDirections.toGraphLogin()
        navigator.navigateTo(direction)
    }

    private fun navigateToMain() {
        val direction = SplashFragmentDirections.toGraphMain()
        navigator.navigateTo(direction)
    }
}
