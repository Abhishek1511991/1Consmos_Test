package com.test.a1consmostest.model


import com.google.gson.annotations.SerializedName

public data class Json4Kotlin_Base (

	@SerializedName("page") val page : Int,
	@SerializedName("per_page") val per_page : Int,
	@SerializedName("total") val total : Int,
	@SerializedName("total_pages") val total_pages : Int,
	@SerializedName("data") val data : List<Data>,
	@SerializedName("support") val support : Support
)