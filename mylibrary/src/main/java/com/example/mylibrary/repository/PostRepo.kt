package com.example.mylibrary.repository

import android.util.Log
import com.example.mylibrary.model.commentReponse.CommentReponse
import com.example.mylibrary.Resorces
import com.example.mylibrary.Util
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.request.PostImageRequest
import com.example.mylibrary.model.ui.CommetReponseUI
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.network.RetrofitApiInterface
import javax.inject.Inject

class PostRepo @Inject constructor( val api:RetrofitApiInterface) {


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


    suspend fun getComment(commentPostRequest: CommentPostRequest):Resorces<CommetReponseUI>{
        try {
         val reponse=   api.getPostComments(commentPostRequest.postId)
            return if (reponse.isSuccessful){
                if (reponse.body()==null){
                    Resorces.Error(null, -1, "Response is null")

                }else{

                    Resorces.Success(CommetReponseUI(commentPostRequest.currentPostionInRecylerView,reponse.body()!!))
                }
            }else{
                Resorces.Error(null, reponse.code(), reponse.message())
            }
            Log.d("PostTAG","${reponse.body()?.size}")
        }catch (e:Exception){
            e.printStackTrace()
            return Resorces.Error(e, -1, e.message.toString())

        }
    }

    suspend fun getPostImage(postImageRequest: PostImageRequest):String {
        val digest= Util.toHexString(Util.getSHA(postImageRequest.postTitle.replace(" ", "")))
        val imageUrl="https://picsum.photos/seed/${digest}/200/300"
        return imageUrl
    }

}