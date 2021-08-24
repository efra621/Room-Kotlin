package app.gratumain.roomandroid.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import app.gratumain.roomandroid.data.model.Notes

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)   //No tengo ni idea de q sea
    suspend fun addData(notes: Notes)

    @Query("SELECT * FROM notes ORDER BY id ASC")
    fun readAllNotes():LiveData<List<Notes>>

    @Update
    suspend fun updateNote(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}