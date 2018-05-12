package org.meklu.uni.otm.domain

import org.meklu.uni.otm.model.User

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
        val u = User(db, 0, "").find("username", username) ?: return false
        if (username.equals(u.username)) {
            this.user = u
            return true
        }
        return false
    }

    fun register(username : String) : Boolean {
        return User(db, 0, username).save()
    }
}
