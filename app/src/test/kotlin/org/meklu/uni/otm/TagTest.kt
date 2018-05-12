package org.meklu.uni.otm

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.meklu.uni.otm.domain.Database
import org.meklu.uni.otm.model.Tag
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TagTest {
    private val db : Database = Database("test.db")

    @Before
    fun `reset database`() {
        db.reset()
    }

    @After
    fun `close connection`() {
        db.close()
    }

    @Test
    fun `creating a tag works`() {
        val r = Tag(db, 0, "foobarzot")
        assert(r.save())
        assertNotEquals(0, r.id)
        val found = r.find("id", r.id.toString())
        assertEquals(r.id, found?.id)
        assertEquals("foobarzot", found?.tag)
    }

    @Test
    fun `forcing a tag id on creation works`() {
        val r = Tag(db, 1, "foobarzot")
        assert(r.save())
        assertEquals(1, r.id)
        val found = r.find("id", r.id.toString())
        assertEquals(1, found?.id)
        assertEquals("foobarzot", found?.tag)
    }

    @Test
    fun `findLike works`() {
        assert(Tag(db, 0, "foobarzot").save())
        assert(Tag(db, 0, "barkeep").save())
        assert(Tag(db, 0, "zotter").save())
        val found = Tag(db, 0, "foo").findLike("tag", "%bar%")
        assertEquals(2, found?.size)
    }

    @Test
    fun `deletion works`() {
        assert(Tag(db, 0, "foobarzot").save())
        assert(Tag(db, 0, "barkeep").save())
        assert(Tag(db, 0, "zotter").save())
        assert(Tag(db, 1, "").delete())
        val found = Tag(db, 0, "foo").findLike("tag", "%")
        assertEquals(2, found?.size)
    }
}
