package com.example.numbers.data.networking

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FactsService {
    @GET("{number}")
    fun getFact(
        @Path("number") number: Int
    ): Call<ResponseBody>

    @GET("random/math")
    fun getFactRandom(): Call<ResponseBody>
}