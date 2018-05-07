package org.meklu.uni.otm

import javafx.application.Application
import javafx.stage.Stage

import java.sql.*;

class Main : Application() {
    override fun start(stage : Stage) {
        stage.scene = LoginScreen().scene
        stage.show()
    }

    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            // FIXME: remove these lines
            val conn = DriverManager.getConnection("jdbc:sqlite:test.db")
            val stmt = conn.prepareStatement("SELECT CURRENT_TIMESTAMP as ts")
            val res = stmt.executeQuery();
            while (res.next()) {
                val date = res.getString("ts")
                println(date)
            }
            launch(Main::class.java)
        }
    }
}