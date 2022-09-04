package com.ayomicakes.app.navigation

import com.ayomicakes.app.screen.home.NavigationDestination
import com.ayomicakes.app.screen.home.Screens
import kotlinx.coroutines.flow.MutableStateFlow

class Navigator {

    var destination: MutableStateFlow<NavigationDestination> = MutableStateFlow(Screens.ShopsPage)

    fun navigate(destination: NavigationDestination) {
        this.destination.value = destination
    }

}