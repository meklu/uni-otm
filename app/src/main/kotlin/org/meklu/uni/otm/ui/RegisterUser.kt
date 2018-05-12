package org.meklu.uni.otm.ui

import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage

class RegisterUser {
    val stage: Stage

    constructor(gui : GUI) {
        val logic = gui.logic
        val layout = VBox()
        val scene = Scene(layout, 380.0, 380.0)
        stage = Stage()
        stage.scene = scene
        stage.title = "Register"
        val userLabel = Label("User name")
        val userField = TextField()
        val registerButton = Button("Register")
        val backButton = Button("Back")

        val backAction = {
            userField.clear()
            gui.stage = gui.loginScreen.stage
        }

        backButton.setOnAction({_ -> backAction()})

        registerButton.setOnAction({ _ -> if (logic.register(userField.text)) {
            println("Registering successful for user " + userField.text)
            backAction()
        } else {
            val a = Alert(Alert.AlertType.ERROR)
            a.initOwner(stage)
            a.title = "Error!"
            a.headerText = "Error while registering!"
            a.contentText = "Registering failed for user ${userField.text}."
            a.showAndWait()
        }})
        userField.onAction = registerButton.onAction

        layout.children.addAll(userLabel, userField, registerButton, backButton)
    }
}
