package org.meklu.uni.otm.model

class User : Model {
    override fun tableName(): String = "users"

    val id : Int
    val username : String

    constructor(
        id : Int,
        username : String
    ) {
        this.id = id
        this.username = username
    }
}
