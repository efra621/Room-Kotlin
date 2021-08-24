package app.gratumain.roomandroid.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import app.gratumain.roomandroid.data.Database
import app.gratumain.roomandroid.data.model.Notes
import app.gratumain.roomandroid.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) :AndroidViewModel(application) {

    private val repository : Repository
    val readAllNotes:LiveData<List<Notes>>

    init {
        val dao = Database.getDatabase(application).dao()
        repository = Repository(dao)

        readAllNotes = repository.readAllNotes
    }

    fun addData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.addData(notes)
        }
    }

    fun update(notes: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateData(notes)
        }
    }

    fun deleteData(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(notes)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }
}