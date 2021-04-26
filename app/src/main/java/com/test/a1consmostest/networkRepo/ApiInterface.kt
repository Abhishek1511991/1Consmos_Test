package com.test.a1consmostest.networkRepo

import com.test.a1consmostest.model.Json4Kotlin_Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api/users")
     fun getTopRecordList(@Query("page") page : Int? = null): Call<Json4Kotlin_Base>

}