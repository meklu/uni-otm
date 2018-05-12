package org.meklu.uni.otm.model

import org.meklu.uni.otm.domain.Database
import java.sql.ResultSet

class User : Model<User> {
    override fun tableName(): String = "users"

    val db : Database
    var id : Int
    var username : String

    constructor(
        db : Database,
        id : Int,
        username : String
    ) {
        this.db = db
        this.id = id
        this.username = username
    }

    override fun save(): Boolean {
        val fields = ArrayList<Pair<String, String>>()
        fields.add(Pair("username", username))
        if (this.id > 0) {
            fields.add(Pair("id", id.toString()))
        }
        val id = db.saveReturningId(this.tableName(), fields)
        if (id == -1) return false
        this.id = id
        return true
    }

    override fun find(field: String, value: String): User? {
        val res = db.find(this.tableName(), field, value) ?: return null
        if (!res.next()) return null
        return this.fromResultSet(res)
    }

    override fun findLike(field: String, pattern: String): List<User> {
        val ret = ArrayList<User>()
        val res = db.findLike(this.tableName(), field, pattern) ?: return ret
        while (res.next()) {
            ret.add(this.fromResultSet(res))
        }
        return ret
    }

    override fun delete(): Boolean {
        return db.delete(this.tableName(), "id", id.toString())
    }

    override fun fromResultSet(rs: ResultSet): User {
        val id = rs.getInt("id")
        val username = rs.getString("username")
        return User(db, id, username)
    }
}
