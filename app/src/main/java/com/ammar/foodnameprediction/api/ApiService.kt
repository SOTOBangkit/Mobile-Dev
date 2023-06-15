package com.ammar.foodnameprediction.api

import com.ammar.foodnameprediction.Result
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Multipart
    @POST("process")
    fun uploadImage( @Part image:MultipartBody.Part):Call<Result>


}