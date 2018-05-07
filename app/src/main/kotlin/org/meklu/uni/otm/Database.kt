package org.meklu.uni.otm

import java.sql.Connection
import java.sql.DriverManager

class Database {
    private val uri : String
    private val conn : Connection

    constructor (file : String) {
        uri = "jdbc:sqlite:$file"
        conn = DriverManager.getConnection(uri)
    }

    fun getConn () = conn
}