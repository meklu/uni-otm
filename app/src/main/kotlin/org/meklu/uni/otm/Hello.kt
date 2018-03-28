package org.meklu.uni.otm

class Hello {
    fun str() = "Hello world!"
    fun hi() = println(this.str())

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val h = Hello();
            h.hi()
        }
    }
}
