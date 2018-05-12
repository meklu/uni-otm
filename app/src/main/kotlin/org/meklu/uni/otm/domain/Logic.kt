package org.meklu.uni.otm.domain

import org.meklu.uni.otm.model.User
import java.sql.SQLException

class Logic {
    val db : Database
    var user : User? = null

    constructor(
        db : Database
    ) {
        this.db = db
        this.db.createTables()
    }

    fun login(username : String) : Boolean {
        return try {
            val u = User(db, 0, "").find("username", username) ?: return false
            if (username == u.username) {
                this.user = u
                return true
            }
            false
        } catch (e : SQLException) {
            false
        }
    }

    fun register(username : String) : Boolean {
        return try {
            User(db, 0, username).save()
        } catch (e : SQLException) {
            false
        }
    }
}
