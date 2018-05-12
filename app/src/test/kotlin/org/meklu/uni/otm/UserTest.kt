package org.meklu.uni.otm

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.meklu.uni.otm.domain.Database
import org.meklu.uni.otm.model.User
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UserTest {
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
    fun `creating a user works`() {
        val u = User(db, 0, "foobarzot")
        assert(u.save())
        assertNotEquals(0, u.id)
        val found = u.find("id", u.id.toString())
        assertEquals(u.id, found?.id)
        assertEquals("foobarzot", found?.username)
    }

    @Test
    fun `forcing a user id on creation works`() {
        val u = User(db, 1, "foobarzot")
        assert(u.save())
        assertEquals(1, u.id)
        val found = u.find("id", u.id.toString())
        assertEquals(1, found?.id)
        assertEquals("foobarzot", found?.username)
    }

    @Test
    fun `findLike works`() {
        assert(User(db, 0, "foobarzot").save())
        assert(User(db, 0, "barkeep").save())
        assert(User(db, 0, "zotter").save())
        val found = User(db, 0, "foo").findLike("username", "%bar%")
        assertEquals(2, found?.size)
    }

    @Test
    fun `deletion works`() {
        assert(User(db, 0, "foobarzot").save())
        assert(User(db, 0, "barkeep").save())
        assert(User(db, 0, "zotter").save())
        assert(User(db, 1, "").delete())
        val found = User(db, 0, "foo").findLike("username", "%")
        assertEquals(2, found?.size)
    }
}
