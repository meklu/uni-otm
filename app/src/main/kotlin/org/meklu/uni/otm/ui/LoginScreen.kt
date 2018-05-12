package org.meklu.uni.otm.ui

import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.control.TextField
import javafx.scene.control.Label
import javafx.scene.control.Button
import org.meklu.uni.otm.domain.Logic

class LoginScreen {
    val logic: Logic
    val scene: Scene
    val layout: VBox
    val userLabel: Label
    val userField: TextField
    val loginButton: Button

    constructor(logic: Logic) {
        this.logic = logic
        layout = VBox()
        scene = Scene(layout, 380.0, 380.0)
        userLabel = Label("User name")
        userField = TextField()
        loginButton = Button("Log in")

        loginButton.setOnAction({ _ -> if (logic.login(userField.text)) {
            println("Login successful for user " + userField.text)
            userField.clear()
        } else {
            println("Login failed for user " + userField.text)
        }})
        layout.children.addAll(userLabel, userField, loginButton)
    }
}