package com.example.mylibrary.model.ui

import com.example.example.UserReponse
import com.example.mylibrary.model.ModelResponsePost

class PostUi(userId: Int, id: Int, title: String, body: String, var author :UserReponse?=null, var comments :Int=-1) :
    ModelResponsePost(userId, id, title, body)