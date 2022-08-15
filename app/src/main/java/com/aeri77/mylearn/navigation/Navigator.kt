package com.aeri77.mylearn.navigation

import com.aeri77.mylearn.screen.home.NavigationDestination
import com.aeri77.mylearn.screen.home.Screens
import kotlinx.coroutines.flow.MutableStateFlow

class Navigator {

    var destination: MutableStateFlow<NavigationDestination> = MutableStateFlow(Screens.ShopsPage)

    fun navigate(destination: NavigationDestination) {
        this.destination.value = destination
    }

}