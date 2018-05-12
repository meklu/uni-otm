package org.meklu.uni.otm.ui

import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.meklu.uni.otm.model.Snippet
import org.meklu.uni.otm.model.Tag

class ListSnippets {
    val stage: Stage

    constructor(gui : GUI) {
        val logic = gui.logic
        val layout = VBox()
        val scene = Scene(layout, 380.0, 380.0)
        stage = Stage()
        stage.scene = scene
        stage.title = "Snippets"
        val snippetLabel = Label("Snippets")

        val table = TableView<Snippet>()
        table.isEditable = true
        val snippetCol = TableColumn<Snippet, String>("Code")
        val tagsCol = TableColumn<Snippet, List<Tag>>("Tags")
        table.columns.addAll(snippetCol, tagsCol)

        val logoutButton = Button("Log out")

        logoutButton.setOnAction({_ ->
            logic.logout()
            gui.stage = gui.loginScreen.stage
        })

        layout.children.addAll(snippetLabel, table, logoutButton)
    }
}
