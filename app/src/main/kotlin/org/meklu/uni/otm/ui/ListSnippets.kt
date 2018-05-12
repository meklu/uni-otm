package org.meklu.uni.otm.ui

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
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

        val snippets = FXCollections.observableArrayList<Snippet>()
        snippetCol.cellValueFactory = PropertyValueFactory("snippet")
        tagsCol.cellValueFactory = PropertyValueFactory("snippet") // TODO!
        table.items = snippets
        stage.setOnShown({
            snippets.addAll(logic.recentSnippets())
        })

        logoutButton.setOnAction({_ ->
            snippets.clear()
            logic.logout()
            gui.stage = gui.loginScreen.stage
        })

        layout.children.addAll(snippetLabel, table, logoutButton)
    }
}
