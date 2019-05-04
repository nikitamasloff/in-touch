package com.nikitamaslov.core.navigation.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nikitamaslov.core.navigation.Navigator
import com.nikitamaslov.core.navigation.model.NavigationCommand

fun observeNavigation(
    navigator: Navigator,
    navController: NavController,
    lifecycleOwner: LifecycleOwner
) {
    navigator.navigationEvents.observe(lifecycleOwner, Observer { navigationEvent ->
        when (val navigationCommand = navigationEvent.getNavigationCommandIfNotHandled()) {
            is NavigationCommand.To -> navController.navigate(navigationCommand.direction)
            is NavigationCommand.Back -> navController.navigateUp()
        }
    })
}

fun Fragment.observeNavigation(
    navigator: Navigator,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner
) {
    val navController = findNavController()
    observeNavigation(navigator, navController, lifecycleOwner)
}
