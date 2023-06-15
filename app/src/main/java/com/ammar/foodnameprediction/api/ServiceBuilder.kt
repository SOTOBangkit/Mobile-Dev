package com.ammar.foodnameprediction.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ServiceBuilder {
    companion object {

//        private const val BASE_URL = "http://localhost:5000/"
        private const val BASE_URL = "https://coba-coba-dot-soto-capstone.et.r.appspot.com"

        val myApi: ApiService by lazy {
            return@lazy getRetrofit().create(ApiService::class.java)
        }
        @Volatile
        private var INSTANCE: Retrofit? = null

        private fun getRetrofit(): Retrofit {

            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpClient.Builder().build())
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}