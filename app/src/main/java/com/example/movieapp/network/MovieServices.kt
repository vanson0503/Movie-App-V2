package com.example.movieapp.network

import com.example.movieapp.utils.Constrain.BASE_URL_API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieServices {
    val api : MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}