package com.test.a1consmostest.DbRepo


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.a1consmostest.model.Data

@Database(entities = arrayOf(Data::class), version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun DataDao():DataDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): NoteDatabase? {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, NoteDatabase::class.java, "notes_database")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }
    }


}