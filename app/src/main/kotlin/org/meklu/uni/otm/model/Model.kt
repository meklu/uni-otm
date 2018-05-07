package org.meklu.uni.otm.model

interface Model {
    /** @return The name of the database table for the Model */
    fun tableName() : String
}
