package app.gratumain.roomandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.gratumain.roomandroid.data.model.Notes

@Database(entities = [Notes::class] ,version = 1, exportSchema = false)
abstract class Database: RoomDatabase(){

    abstract fun dao(): Dao

    //Singleton
    companion object{
        @Volatile
        private var INTANCE : app.gratumain.roomandroid.data.Database? = null //Cuidado Database

        fun getDatabase(context: Context) : app.gratumain.roomandroid.data.Database {
            val instance = INTANCE

            if (instance != null){
                return instance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, app.gratumain.roomandroid.data.Database::class.java, "notes"
                ).build()

                INTANCE = instance
                return instance
            }
        }
    }
}