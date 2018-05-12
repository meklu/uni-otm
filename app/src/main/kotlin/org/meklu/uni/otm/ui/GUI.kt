package org.meklu.uni.otm.ui

import javafx.application.Application
import javafx.stage.Stage
import org.meklu.uni.otm.domain.Logic

class GUI() : Application(), UserInterface {
    private var _stage : Stage? = null
    var stage : Stage? = null
        set(x) {
            x?.sizeToScene()
            x?.show()
            x?.minWidth = x!!.width
            x?.minHeight = x!!.height
            _stage?.hide()
            _stage = x
        }
    val logic : Logic

    val loginScreen : LoginScreen
    val registerUser : RegisterUser

    init {
        this.logic = GUI.logic!!

        this.loginScreen = LoginScreen(this)
        this.registerUser = RegisterUser(this)
    }

    override fun start(stage: Stage) {
        this.stage = this.loginScreen.stage
    }

    companion object {
        private var logic : Logic? = null

        fun launch(logic: Logic) {
            this.logic = logic
            Application.launch(GUI::class.java)
        }
    }
}
