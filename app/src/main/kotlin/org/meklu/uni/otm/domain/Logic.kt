package org.meklu.uni.otm.domain

import org.meklu.uni.otm.model.Snippet
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

    fun logout() { user = null }

    fun register(username : String) : Boolean {
        return try {
            User(db, 0, username).save()
        } catch (e : SQLException) {
            false
        }
    }

    fun recentSnippets() : List<Snippet> {
        return try {
            Snippet(db, 0, 0, false, "").findWhere(listOf(
                Triple("owner_id", "=", user!!.id.toString())
            ),listOf(
                "ORDER BY created DESC"
            ))
        } catch (e : SQLException) {
            listOf()
        }
    }
}
