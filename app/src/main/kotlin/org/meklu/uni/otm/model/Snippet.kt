package org.meklu.uni.otm.model

class Snippet : Model {
    override fun tableName(): String = "snippets"

    val id : Int
    val ownerId : Int
    val isPublic : Boolean
    val snippet : String

    constructor(
        id : Int,
        ownerId : Int,
        isPublic : Boolean,
        snippet : String
    ) {
        this.id = id
        this.ownerId = ownerId
        this.isPublic = isPublic
        this.snippet = snippet
    }
}
