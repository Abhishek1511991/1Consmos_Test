package com.test.a1consmostest.DbRepo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.a1consmostest.model.Data

@Dao
interface DataDao {

    @Insert
    fun insert(note: Data)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun InsertData(dataTableModel: Data)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTodoList(todoList: List<Data?>?)



    @Query("SELECT * FROM data_table LIMIT 10 OFFSET:offset")
    fun getAllNotes(offset: Int): LiveData<List<Data>>


    @Query("SELECT COUNT(id) FROM data_table")
    fun getCount(): Int
}