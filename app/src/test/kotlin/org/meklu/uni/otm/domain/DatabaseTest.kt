package org.meklu.uni.otm.domain

import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse

class DatabaseTest {
    private val db : Database

    constructor() {
        db = Database("test.db")
    }

    @Before
    fun `reset database`() {
        db.reset()
    }

    @After
    fun `close connection`() {
        db.close()
    }

    private fun `table is empty`(table : String) {
        val conn = db.getConn()
        assertFalse(conn.prepareStatement("SELECT * FROM $table").executeQuery().next())
    }

    @Test
    fun `the users table is empty`() {
        this.`table is empty`("users")
    }

    @Test
    fun `the snippets table is empty`() {
        this.`table is empty`("snippets")
    }

    @Test
    fun `the tags table is empty`() {
        this.`table is empty`("tags")
    }

    @Test
    fun `the tags_snippets table is empty`() {
        this.`table is empty`("tags_snippets")
    }
}
