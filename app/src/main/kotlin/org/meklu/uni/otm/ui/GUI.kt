package org.meklu.uni.otm.ui

import javafx.application.Application
import javafx.stage.Stage
import org.meklu.uni.otm.domain.Logic

class GUI() : Application(), UserInterface {
    val logic : Logic

    val loginScreen : LoginScreen

    init {
        this.logic = GUI.logic!!

        this.loginScreen = LoginScreen(logic)
    }

    override fun start(stage: Stage) {
        stage.scene = this.loginScreen.scene
        stage.show()
    }

    companion object {
        private var logic : Logic? = null

        fun launch(logic: Logic) {
            this.logic = logic
            Application.launch(GUI::class.java)
        }
    }
}
