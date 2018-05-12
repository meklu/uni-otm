package org.meklu.uni.otm.domain

import java.io.File
import java.io.InputStream
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

    /** Closes the connection
     * @see java.sql.Connection
     */
    fun close() = conn.close()

    /** Private utility function that reads a file as a string
     *
     * @param file The path to the file being read
     * @return The file contents
     */
    private fun readFile(file : String) : String {
        val stream: InputStream = File(file).inputStream()
        return stream.bufferedReader().use { it.readText() }
    }

    /** A hacky private utility function that executes an SQL file
     * that only contains DDL statements
     *
     * @param file The path to the executed file
     */
    private fun execFile(file : String) {
        startTransaction()
        val parts = readFile(file).split(";")
        for (p in parts) {
            val q = p.trim()
            this.conn.createStatement().apply({
                closeOnCompletion()
                executeUpdate(q)
            })
        }
        commit()
    }

    /** Drops all the tables in the database
     *
     * CAUTION: Removes everything!
     */
    fun dropTables() {
        execFile("sql/drop_tables.sql")
    }

    /** Re-creates all the tables in the database, if necessary
     */
    fun createTables() {
        execFile("sql/create_tables.sql")
    }

    /** Resets the database
     *
     * CAUTION: Drops all tables and re-creates them
     */
    fun reset() {
        this.dropTables()
        this.createTables()
    }

    /** Finds rows where field = value
     *
     * @param table The table to query
     * @param field The field to check against
     * @param value The value to compare the field to
     * @return Possibly a ResultSet
     */
    fun find(table : String, field : String, value : String): ResultSet {
        return findWhere(table, listOf(Triple(field, "LIKE", value)))
    }

    /** Finds rows where field matches the given SQL pattern
     *
     * @param table The table to query
     * @param field The field to check against
     * @param pattern The value to compare the field to
     * @return Possibly a ResultSet
     */
    fun findLike(table : String, field : String, pattern : String): ResultSet {
        return findWhere(table, listOf(Triple(field, "LIKE", pattern)))
    }

    /** Finds rows where the given fields match the given values according to the provided operator
     *
     * @param table The table to query
     * @param fields The fields to check against in (field, operator, value) form, e.g. ("name", "LIKE", "%ant%")
     * @return Possibly a ResultSet
     */
    fun findWhere(table : String, whereFields : List<Triple<String, String, String>>, additionalOrders : List<String> = listOf()): ResultSet {
        val frepl = whereFields.joinToString(separator = " AND ", transform = {x -> "${x.first} ${x.second} ?"})
        val addtl = additionalOrders.joinToString(separator = " ")
        val query = "SELECT * FROM $table WHERE $frepl $addtl"
        val stmt = conn.prepareStatement(query)
        for (i in 1..whereFields.size) {
            val t = whereFields[i - 1]
            stmt.setString(i, t.third)
        }
        return stmt.executeQuery()
    }

    /** Saves a row
     *
     * @param table The table to insert into
     * @param fields A list of pairs of (field, value)
     * @return Whether we succeeded or not
     */
    fun save(table : String, fields : List<Pair<String, String>>): Boolean {
        val frepl = fields.joinToString(separator = ",", transform = {"?"})
        val fnames = fields.joinToString(separator = ",", transform = { x -> x.first })
        val query = "INSERT INTO $table ($fnames) VALUES ($frepl)"
        val stmt : PreparedStatement = conn.prepareStatement(query)
        for (i in 0..fields.size - 1) {
            val off = 1
            stmt.setString(off + i, fields[i].second)
        }
        stmt.execute()
        return stmt.updateCount > 0
    }

    /** Saves a row but returns the inserted item's id as well
     *
     * @param table The table to insert into
     * @param fields A list of pairs of (field, value)
     * @return The id of the newly inserted row
     */
    fun saveReturningId(table : String, fields : List<Pair<String, String>>) : Int {
        this.startTransaction()
        val ret = this.save(table, fields)
        val id = this.lastId(table)
        this.commit()
        if (!ret) return -1
        return id
    }

    /** Starts a transaction
     */
    fun startTransaction() {
        conn.autoCommit = false
    }

    /** Commits a transaction
     */
    fun commit() {
        conn.commit()
        conn.autoCommit = true
    }

    /** Rolls back a transaction
     */
    fun rollback() {
        conn.rollback()
        conn.autoCommit = true
    }

    /** Gets the id of the last inserted row
     *
     * @return The id of the last inserted row
     */
    fun lastId(table : String) : Int {
        val stmt = conn.prepareStatement("SELECT seq FROM sqlite_sequence WHERE name = ?")
        stmt.setString(1, table)
        val res = stmt.executeQuery()
        if (!res.next()) return -1
        return res.getInt("seq")
    }

    /** Deletes a row
     *
     * @param table The table to delete from
     * @param field The field to delete by
     * @param value The value to delete by
     * @return Whether we succeeded
     */
    fun delete(table : String, field : String, value : String) : Boolean {
        val l = ArrayList<Pair<String, String>>()
        l.add(Pair(field, value))
        return this.delete(table, l)
    }

    /** Deletes a row
     *
     * @param table The table to delete from
     * @param whereFields The fields according to which the row is deleted as (key, value) pairs. These are ANDed.
     * @return Whether we succeeded
     */
    fun delete(table : String, whereFields : List<Pair<String, String>>) : Boolean {
        val frepl = whereFields.joinToString(separator = " AND ", transform = {x -> "${x.first} = ?"})
        val query = "DELETE FROM $table WHERE $frepl"
        val stmt = conn.prepareStatement(query)
        for (i in 1..whereFields.size) {
            val p = whereFields[i - 1]
            stmt.setString(i, p.second)
        }
        stmt.execute()
        return stmt.updateCount > 0
    }
}
