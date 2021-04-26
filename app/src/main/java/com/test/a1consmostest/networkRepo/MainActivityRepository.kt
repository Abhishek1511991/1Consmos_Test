package com.test.a1consmostest.networkRepo


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.a1consmostest.DbRepo.DatabaseRepo
import com.test.a1consmostest.model.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityRepository(application: Application): AndroidViewModel(application) {

    val serviceSetterGetter = MutableLiveData<Json4Kotlin_Base>()
    private var repository: DatabaseRepo = DatabaseRepo(application)

    fun getServicesApiCall(page:Int): MutableLiveData<Json4Kotlin_Base> {

        val call = RetrofitClient.apiInterface.getTopRecordList(page)

        call.enqueue(object: Callback<Json4Kotlin_Base> {
            override fun onFailure(call: Call<Json4Kotlin_Base>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<Json4Kotlin_Base>,
                response: Response<Json4Kotlin_Base>
            ) {
                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                try {
                    repository.insert(data?.data)
                }
                catch (e:Exception)
                {
                    e.stackTrace
                }
                serviceSetterGetter.postValue(data)
            }
        })

        return serviceSetterGetter
    }
}