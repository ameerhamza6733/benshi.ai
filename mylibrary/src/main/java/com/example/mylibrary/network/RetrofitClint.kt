package com.example.mylibrary.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClint {
     private var retrofit:Retrofit ?=null

    fun getInstant(): RetrofitApiInterface {
        if (retrofit==null){

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
                .callTimeout(unit = TimeUnit.SECONDS, timeout = 30)
                .retryOnConnectionFailure(true)
                .build()

           retrofit= Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
               .client(client)
               .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
       return retrofit!!.create(RetrofitApiInterface::class.java)
    }
}