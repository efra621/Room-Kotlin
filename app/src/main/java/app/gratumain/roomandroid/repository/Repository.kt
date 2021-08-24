package app.gratumain.roomandroid.repository

import androidx.lifecycle.LiveData
import app.gratumain.roomandroid.data.Dao
import app.gratumain.roomandroid.data.model.Notes

class Repository (private val dao: Dao) {

    suspend fun addData(notes: Notes){
        dao.addData(notes)
    }

    val readAllNotes:LiveData<List<Notes>> = dao.readAllNotes()

    suspend fun updateData(notes: Notes){
        dao.updateNote(notes)
    }

    suspend fun deleteData(notes: Notes){
        dao.delete(notes)
    }

    suspend fun deleteAllData(){
        dao.deleteAllNotes()
    }
}