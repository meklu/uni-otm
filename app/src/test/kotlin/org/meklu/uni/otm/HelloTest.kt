package org.meklu.uni.otm

import org.junit.Test
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun strWorks() {
        val h = Hello()
        assertEquals("Hello world!", h.str())
    }

    @Test
    fun hiWorks() {
        val h = Hello()
        h.hi()
    }

    @Test
    fun mainThere() {
        Hello.main(arrayOf())
    }
}
