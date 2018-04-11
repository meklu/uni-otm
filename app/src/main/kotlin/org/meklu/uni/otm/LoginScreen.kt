package org.meklu.uni.otm

import javafx.scene.Scene
import javafx.scene.layout.VBox
import javafx.scene.control.TextField
import javafx.scene.control.Label
import javafx.scene.control.Button

class LoginScreen {
    val scene: Scene
    val layout: VBox
    val userLabel: Label
    val userField: TextField
    val loginButton: Button

    constructor() {
        layout = VBox()
        scene = Scene(layout, 380.0, 380.0)
        userLabel = Label("User name")
        userField = TextField()
        loginButton = Button("Log in")
        layout.children.addAll(userLabel, userField, loginButton)
    }
}