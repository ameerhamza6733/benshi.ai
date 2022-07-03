package com.example.mylibrary.repository

import com.example.mylibrary.Resorces
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.network.RetrofitClint

class PostRepo {
    val api=RetrofitClint.getInstant()

    suspend fun getPost(): Resorces<ArrayList<PostUi>> {
     return try {
         val reponse= api.getPosts()
          if (reponse.isSuccessful){
              if (reponse.body()==null){
                  return Resorces.Error(null, -1, "Response is null")

              }else{

                  return Resorces.Success(reponse.body()!!)
              }
          }else{
              return Resorces.Error(null, reponse.code(), reponse.message())
          }
      }catch (e:Exception){
          e.printStackTrace()
         return Resorces.Error(e, -1, e.message.toString())
      }

    }


    suspend fun getComment(commentPostRequest: CommentPostRequest){
        try {
            api.getPostComments(commentPostRequest.postId)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    suspend fun getPostImage(encodedhash: String):String {
        val imageUrl="https://picsum.photos/seed/${encodedhash}/200/300"
        return imageUrl
    }

}