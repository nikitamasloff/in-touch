package com.nikitamaslov.core.navigation.model

data class NavigationEvent(private val navigationCommand: NavigationCommand) {

    var hasBeenHandled: Boolean = false
        private set

    fun getNavigationCommandIfNotHandled(): NavigationCommand? =
        navigationCommand.takeIf { !hasBeenHandled }?.also { hasBeenHandled = true }

    fun peekNavigationCommand(): NavigationCommand = navigationCommand
}
