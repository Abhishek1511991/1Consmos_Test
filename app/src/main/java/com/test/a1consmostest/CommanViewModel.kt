package com.test.a1consmostest


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.test.a1consmostest.DbRepo.DatabaseRepo
import com.test.a1consmostest.model.Data

class CommanViewModel(application: Application): AndroidViewModel(application){


    private var repository: DatabaseRepo =DatabaseRepo(application)
    //private var mainRepo: MainActivityRepository =com.test.a1consmostest.networkRepo.MainActivityRepository(application)
    private var allNotes: LiveData<List<Data>>? = null

    var servicesLiveData: MutableLiveData<Int>? = null


    val results = MediatorLiveData<List<Data>>()


    fun insert(note: Data) {
        repository.insert(note)
    }

    fun getAllSavedRecord(page: Int): LiveData<List<Data>> {

        allNotes=repository.getAllNotes(page)
        return allNotes as LiveData<List<Data>>
    }

    fun getNumFiles(): Int {
        return repository.getNumFiles()
    }



}