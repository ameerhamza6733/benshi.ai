package com.example.mylibrary.model.ui

import com.example.mylibrary.model.userReponse.UserReponse
import com.example.mylibrary.model.ModelResponsePost

open class PostUi(userId: Int, id: Int, title: String, body: String, var author : UserReponse?=null, var comments :Int=-1, var postImageUrl:String?=null) :
    ModelResponsePost(userId, id, title, body) {

}