package org.meklu.uni.otm

import org.meklu.uni.otm.domain.Database
import org.meklu.uni.otm.domain.Logic
import org.meklu.uni.otm.ui.GUI

class Main {
    companion object {
        @JvmStatic
        fun main(args : Array<String>) {
            GUI.launch(Logic(Database("snippetdb.db")))
        }
    }
}
