package org.meklu.uni.otm.model

import org.meklu.uni.otm.domain.Database
import java.sql.ResultSet

class Snippet : Model {
    override fun tableName(): String = "snippets"

    val db : Database
    var id : Int
    var ownerId : Int
    var isPublic : Boolean
    var snippet : String

    constructor(
        db : Database,
        id : Int,
        ownerId : Int,
        isPublic : Boolean,
        snippet : String
    ) {
        this.db = db
        this.id = id
        this.ownerId = ownerId
        this.isPublic = isPublic
        this.snippet = snippet
    }

    override fun save(): Boolean {
        val fields = ArrayList<Pair<String, String>>()
        fields.add(Pair("ownerId", ownerId.toString()))
        fields.add(Pair("isPublic", ({if (isPublic) 1 else 0}) .toString()))
        fields.add(Pair("snippet", snippet))
        if (this.id > 0) {
            fields.add(Pair("id", id.toString()))
        }
        val id = db.saveReturningId(this.tableName(), fields)
        if (id == -1) return false
        this.id = id
        return true
    }

    override fun find(field: String, value: String): Model? {
        val res = db.find(this.tableName(), field, value) ?: return null
        if (!res.next()) return null
        return this.fromResultSet(res)
    }

    override fun findLike(field: String, pattern: String): List<Model> {
        val ret = ArrayList<User>()
        val res = db.findLike(this.tableName(), field, pattern) ?: return ret
        while (res.next()) {
            ret.plus(this.fromResultSet(res))
        }
        return ret
    }

    override fun delete(): Boolean {
        return db.delete(this.tableName(), "id", id.toString())
    }

    override fun fromResultSet(rs: ResultSet): Model {
        val id = rs.getInt("id")
        val ownerId = rs.getInt("owner_id")
        val isPublic = rs.getBoolean("is_public")
        val snippet = rs.getString("snippet")
        return Snippet(db, id, ownerId, isPublic, snippet)
    }
}
