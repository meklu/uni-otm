package org.meklu.uni.otm.model

import org.meklu.uni.otm.domain.Database
import java.sql.ResultSet

class Tag : Model<Tag> {
    override fun tableName(): String = "tags"

    val db : Database
    var id : Int
    var tag : String

    constructor(
        db : Database,
        id : Int,
        tag : String
    ) {
        this.db = db
        this.id = id
        this.tag = tag
    }

    override fun save(): Boolean {
        val fields = ArrayList<Pair<String, String>>()
        fields.add(Pair("tag", this.tag))
        if (this.id > 0) {
            fields.add(Pair("id", this.id.toString()))
        }
        val id = db.saveReturningId(this.tableName(), fields)
        if (id == -1) return false
        this.id = id
        return true
    }

    override fun find(field: String, value: String): Tag? {
        val res = db.find(this.tableName(), field, value)
        if (!res.next()) return null
        return this.fromResultSet(res)
    }

    override fun findLike(field: String, pattern: String): List<Tag> {
        val ret = ArrayList<Tag>()
        val res = db.findLike(this.tableName(), field, pattern)
        while (res.next()) {
            ret.add(this.fromResultSet(res))
        }
        return ret
    }

    override fun findWhere(fields: List<Triple<String, String, String>>, additionalOrders : List<String>): List<Tag> {
        val ret = ArrayList<Tag>()
        val res = db.findWhere(this.tableName(), fields, additionalOrders)
        while (res.next()) {
            ret.add(this.fromResultSet(res))
        }
        return ret
    }

    override fun delete(): Boolean {
        return db.delete(this.tableName(), "id", id.toString())
    }

    override fun fromResultSet(rs: ResultSet): Tag {
        val id = rs.getInt("id")
        val tag = rs.getString("tag")
        return Tag(db, id, tag)
    }
}
