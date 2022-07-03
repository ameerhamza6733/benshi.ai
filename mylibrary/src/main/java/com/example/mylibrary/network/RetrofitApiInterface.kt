package com.example.mylibrary.network

import com.example.example.UserReponse
import com.example.mylibrary.model.ui.PostUi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RetrofitApiInterface {
    @GET("posts")
    suspend fun getPosts():Response<ArrayList<PostUi>>

    @GET("users/{userid}")
    suspend fun getUserDetail(@Path("userid")userid:Int):Response<UserReponse>

    @GET("comments/{post_id}")
    suspend fun getPostComments(@Path("post_id")post_id:Int):Response<Any>


}