package org.meklu.uni.otm

import javafx.application.Application
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage : Stage) {
        stage.scene = LoginScreen().scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            launch(Main::class.java)
        }
    }
}