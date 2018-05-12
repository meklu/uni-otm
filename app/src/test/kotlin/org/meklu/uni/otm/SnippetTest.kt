package org.meklu.uni.otm

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.meklu.uni.otm.domain.Database
import org.meklu.uni.otm.model.Snippet
import org.meklu.uni.otm.model.User
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SnippetTest {
    private val db : Database = Database("test.db")

    @Before
    fun `reset database`() {
        db.reset()
        User(db, 0, "testuser").save()
    }

    @After
    fun `close connection`() {
        db.close()
    }

    @Test
    fun `creating a snippet works`() {
        val r = Snippet(db, 0, 1, true, ":(){:|:&};:")
        assert(r.save())
        assertNotEquals(0, r.id)
        val found = r.find("id", r.id.toString())
        assertEquals(r.id, found?.id)
        assertEquals(":(){:|:&};:", found?.snippet)
    }

    @Test
    fun `forcing a snippet id on creation works`() {
        val r = Snippet(db, 1, 1, true, ":(){:|:&};:")
        assert(r.save())
        assertEquals(1, r.id)
        val found = r.find("id", r.id.toString())
        assertEquals(1, found?.id)
        assertEquals(":(){:|:&};:", found?.snippet)
    }

    @Test
    fun `findLike works`() {
        assert(Snippet(db, 0, 1, true, ":(){:|:&};:").save())
        assert(Snippet(db, 0, 1, true, ":bar:").save())
        assert(Snippet(db, 0, 1, false, "zot").save())
        val found = Snippet(db, 0, 1, true, "").findLike("snippet", "%:%")
        assertEquals(2, found?.size)
    }

    @Test
    fun `deletion works`() {
        assert(Snippet(db, 0, 1, true, ":(){:|:&};:").save())
        assert(Snippet(db, 0, 1, true, ":bar:").save())
        assert(Snippet(db, 0, 1, false, "zot").save())
        assert(Snippet(db, 1, 0, true, "").delete())
        val found = Snippet(db, 0, 1, true, "").findLike("snippet", "%")
        assertEquals(2, found?.size)
    }
}
