package com.test.a1consmostest.DbRepo


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.a1consmostest.model.Data
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DatabaseRepo(application: Application): AndroidViewModel(application)  {

    private var noteDao: DataDao

    private var allNotes: LiveData<List<Data>>?=null

    init {
        val database: NoteDatabase = NoteDatabase.getInstance(
            application.applicationContext
        )!!
        noteDao = database.DataDao()
    }

    fun insert(note: Data) = GlobalScope.launch {
        noteDao.insert(note)
    }

    fun insert(note: List<Data?>?) = GlobalScope.launch {
        noteDao.insertTodoList(note)
    }

    fun getAllNotes(offset:Int): LiveData<List<Data>> {
        allNotes=noteDao?.getAllNotes(offset)
        return allNotes as LiveData<List<Data>>
    }

    fun getNumFiles(): Int {
        return noteDao.getCount()
    }
}