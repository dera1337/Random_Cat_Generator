package com.example.randomcatgenerator.api


import retrofit2.Call
import retrofit2.http.GET


//https://api.thecatapi.com/v1/images/search?api_key=d10fe9b7-cfa2-4859-bad6-635bd303e9f4

const val BASE_URL = "https://api.thecatapi.com/v1/images/"
//const val API_KEY = "d10fe9b7-cfa2-4859-bad6-635bd303e9f4"

interface CatService {
    @GET("search?api_key=d10fe9b7-cfa2-4859-bad6-635bd303e9f4")
    fun randomCat(): Call<List<Cat>>
}