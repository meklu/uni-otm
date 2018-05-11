package org.meklu.uni.otm.model

import java.sql.ResultSet

interface Model {
    /** @return The name of the database table for the Model */
    fun tableName() : String
    /** Saves a Model in the database */
    fun save() : Boolean
    /** Fetches a Model by field */
    fun find(
        field : String,
        value : String
    ) : Model?
    /** Fetches a Model by field, matching a pattern */
    fun findLike(
        field : String,
        pattern : String
    ) : List<Model>
    /** Deletes a Model from the database */
    fun delete() : Boolean
    /** Returns a Model based on a DB result */
    fun fromResultSet(rs : ResultSet) : Model
}
