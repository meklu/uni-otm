package org.meklu.uni.otm

import java.sql.Connection
import java.sql.DriverManager

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
    }

    /** Gets the connection handle for this instance
     *
     * @return The Connection object associated with this
     * @see java.sql.Connection
     */
    fun getConn () = conn
}
