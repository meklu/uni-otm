package org.meklu.uni.otm.model

class Tag : Model {
    override fun tableName(): String = "tags"

    val id : Int
    val tag : String

    constructor(
        id : Int,
        tag : String
    ) {
        this.id = id
        this.tag = tag
    }
}
