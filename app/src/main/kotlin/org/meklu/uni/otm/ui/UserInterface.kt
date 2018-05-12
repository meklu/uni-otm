package org.meklu.uni.otm.ui

import org.meklu.uni.otm.domain.Logic

interface UserInterface {
    companion object {
        /** Launches the user interface */
        fun launch(logic: Logic) {}
    }
}
