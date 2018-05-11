package org.meklu.uni.otm

import javafx.application.Application
import javafx.stage.Stage
import org.meklu.uni.otm.domain.Database
import org.meklu.uni.otm.model.*
import org.meklu.uni.otm.ui.LoginScreen

class Main : Application() {
    override fun start(stage : Stage) {
        stage.scene = LoginScreen().scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            // FIXME: remove these lines
            val db = Database("test.db")
            val u = User(db, 0, "melker")
            println("u:")
            println("\t${u.id}")
            println("\t${u.username}")
            u.save()
            println("u:")
            println("\t${u.id}")
            println("\t${u.username}")

            launch(Main::class.java)
        }
    }
}