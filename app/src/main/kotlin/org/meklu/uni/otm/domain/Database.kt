package org.meklu.uni.otm.domain

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class Database {
    private val uri : String
    private val conn : Connection

    /** Handles some rudimentary database connection things
     *
     * @constructor Creates and stores a database connection
     * @param file The file name for the SQLite database
     */
    constructor (file : String) {
        uri = "jdbc:sqlite:$file"
        conn = DriverManager.getConnection(uri)
        conn.prepareStatement("PRAGMA foreign_keys = ON").execute()
    }

    /** Gets the connection handle for this instance
     *
     * @return The Connection object associated with this
     * @see java.sql.Connection
     */
    fun getConn () = conn

    fun find(table : String, field : String, value : String): ResultSet? {
        val stmt = conn.prepareStatement("SELECT * FROM ${table} WHERE ${field} = ?")
        stmt.setString(1, value)
        return stmt.executeQuery()
    }

    fun findLike(table : String, field : String, pattern : String): ResultSet? {
        val stmt = conn.prepareStatement("SELECT * FROM ${table} WHERE ${field} LIKE ?")
        stmt.setString(1, pattern)
        return stmt.executeQuery()
    }

    fun save(table : String, fields : List<Pair<String, String>>): Boolean {
        val frepl = fields.joinToString(separator = ",", transform = {"?"})
        val fnames = fields.joinToString(separator = ",", transform = { x -> x.first })
        val query = "INSERT INTO $table ($fnames) VALUES ($frepl)"
        val stmt : PreparedStatement = conn.prepareStatement(query)
        for (i in 0..fields.size - 1) {
            val off = 1
            stmt.setString(off, fields[i].second)
        }
        stmt.execute()
        return stmt.updateCount > 0
    }

    fun saveReturningId(table : String, fields : List<Pair<String, String>>) : Int {
        this.startTransaction()
        val ret = this.save(table, fields)
        val id = this.lastId(table)
        this.commit()
        if (!ret) return -1
        return id
    }

    fun startTransaction() {
        conn.autoCommit = false
    }

    fun commit() {
        conn.commit()
        conn.autoCommit = true
    }

    fun rollback() {
        conn.rollback()
        conn.autoCommit = true
    }

    fun lastId(table : String) : Int {
        val stmt = conn.prepareStatement("SELECT seq FROM sqlite_sequence WHERE name = ?")
        stmt.setString(1, table)
        val res = stmt.executeQuery()
        if (!res.next()) return -1
        return res.getInt("seq")
    }

    fun delete(table : String, field : String, value : String) : Boolean {
        val l = ArrayList<Pair<String, String>>()
        l.plus(Pair(field, value))
        return this.delete(table, l)
    }

    fun delete(table : String, whereFields : List<Pair<String, String>>) : Boolean {
        val frepl = whereFields.joinToString(separator = " AND ", transform = {x -> "${x.first} = ?"})
        val query = "DELETE FROM table WHERE $frepl"
        val stmt = conn.prepareStatement(query)
        for (i in 1..whereFields.size - 1) {
            val p = whereFields[i - 1]
            stmt.setString(i, p.second)
        }
        stmt.execute()
        return stmt.updateCount > 0
    }
}
