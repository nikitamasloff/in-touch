package com.nikitamaslov.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.nikitamaslov.core.navigation.model.NavigationCommand
import com.nikitamaslov.core.navigation.model.NavigationEvent

interface Navigator {

    val navigationEvents: LiveData<NavigationEvent>

    fun navigateTo(direction: NavDirections)

    fun navigateBack()
}

class NavigatorImpl : Navigator {

    private val _navigationEvents = MutableLiveData<NavigationEvent>()
    override val navigationEvents: LiveData<NavigationEvent> get() = _navigationEvents

    override fun navigateTo(direction: NavDirections) {
        val navigationCommand = NavigationCommand.To(direction)
        val navigationEvent =
            NavigationEvent(navigationCommand)
        _navigationEvents.value = navigationEvent
    }

    override fun navigateBack() {
        val navigationCommand = NavigationCommand.Back
        val navigationEvent =
            NavigationEvent(navigationCommand)
        _navigationEvents.value = navigationEvent
    }
}
