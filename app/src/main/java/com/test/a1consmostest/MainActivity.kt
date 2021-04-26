package com.test.a1consmostest


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.a1consmostest.model.Data
import com.test.a1consmostest.model.Json4Kotlin_Base
import com.test.a1consmostest.networkRepo.MainActivityRepository
import com.test.a1consmostest.view.AddItem
import com.test.a1consmostest.view.MyRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var noteViewModel: CommanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProvider(this).get(CommanViewModel(application)::class.java)

        val result1: Unit = noteViewModel.getAllSavedRecord(1).observe(this,object:Observer<List<Data>>{
            override fun onChanged(t: List<Data>?) {
                if (t != null) {
                    setAdapter(t)
                }
            }

        })


        if(noteViewModel.getAllSavedRecord(1).value==null)
        {

            val model = ViewModelProvider(this).get(MainActivityRepository(application)::class.java)

            model.getServicesApiCall(1).observe(this,object:Observer<Json4Kotlin_Base>{
                override fun onChanged(t: Json4Kotlin_Base?) {
                    Log.e("","")
                    if(t?.data?.size!!>0)
                    {
                        setAdapter(t?.data)
                    }
                }

            })
        }

    }

    fun openAddPage(view: View) {

        val intent= Intent(this,AddItem::class.java)
        with(intent){
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivityForResult(intent,1)
    }

    private fun setAdapter(itemList:List<Data>) {



        recylerview?.apply {


           /* if(adapter?.itemCount!!>0)
            {
                (adapter as MyRecyclerViewAdapter).updateItemList(itemList as ArrayList<Data>)
            }
            else
            {
                layoutManager = LinearLayoutManager(this@MainActivity)
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
                adapter = MyRecyclerViewAdapter(this@MainActivity, itemList as ArrayList<Data>)
            }*/

            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            adapter = MyRecyclerViewAdapter(this@MainActivity, itemList as ArrayList<Data>)


        }
    }


    private fun setRecyclerViewScrollListener() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView!!.layoutManager?.itemCount

            }
        }
        recylerview.addOnScrollListener(scrollListener)
    }


}