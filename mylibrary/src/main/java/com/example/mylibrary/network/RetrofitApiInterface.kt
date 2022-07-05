package com.example.mylibrary.network

import com.example.mylibrary.model.commentReponse.CommentReponse
import com.example.mylibrary.model.ui.CommetReponseUI
import com.example.mylibrary.model.userReponse.UserReponse
import com.example.mylibrary.model.ui.PostUi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiInterface {
    @GET("posts")
    suspend fun getPosts():Response<ArrayList<PostUi>>

    @GET("users/{userid}")
    suspend fun getUserDetail(@Path("userid")userid:Int):Response<UserReponse>

    @GET("comments")
    suspend fun getPostComments(@Query("postId")post_id:Int):Response<List<CommentReponse>>


}