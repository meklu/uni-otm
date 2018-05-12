package org.meklu.uni.otm.ui

import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.layout.VBox
import javafx.scene.control.TextField
import javafx.scene.control.Label
import javafx.scene.control.Button
import javafx.stage.Stage

class LoginScreen {
    val stage: Stage

    constructor(gui : GUI) {
        val logic = gui.logic
        val layout = VBox()
        val scene = Scene(layout, 380.0, 380.0)
        stage = Stage()
        stage.scene = scene
        stage.title = "Log in"
        val userLabel = Label("User name")
        val userField = TextField()
        val loginButton = Button("Log in")
        val registerButton = Button("No account? Register now!")

        loginButton.setOnAction({ _ -> if (logic.login(userField.text)) {
            gui.stage = gui.listSnippets.stage
            userField.clear()
        } else {
            val a = Alert(Alert.AlertType.ERROR)
            a.initOwner(stage)
            a.title = "Error!"
            a.headerText = "Error while logging in!"
            a.contentText = "Login failed for user ${userField.text}."
            a.showAndWait()
        }})
        userField.onAction = loginButton.onAction

        registerButton.setOnAction({_ ->
            gui.stage = gui.registerUser.stage
        })

        layout.children.addAll(userLabel, userField, loginButton, registerButton)
    }
}