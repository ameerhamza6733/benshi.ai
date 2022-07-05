package com.example.mylibrary.repository

import com.example.mylibrary.model.userReponse.UserReponse
import com.example.mylibrary.Resorces
import com.example.mylibrary.model.request.UserDetailRequest
import com.example.mylibrary.network.RetrofitApiInterface
import javax.inject.Inject

class UserRepo @Inject constructor( private val api:RetrofitApiInterface) {

    suspend fun getUserDetail(userDetailRequest: UserDetailRequest): Resorces<UserReponse> {

       return try {
            val userDetailsReponse=  api.getUserDetail(userDetailRequest.userId)
            if (userDetailsReponse.isSuccessful){
                if(userDetailsReponse.body()!=null){
                    val userReponse=userDetailsReponse.body().apply {
                        this!!.currentPositionInRecyclerView =
                            userDetailRequest.currentPostionInRecylerView
                    }
                        Resorces.Success(userReponse!!)
                }else{
                    Resorces.Error(null,-1,"no user found")
                }
            }else{
                Resorces.Error(null,userDetailsReponse.code(),userDetailsReponse.message())

            }
        }catch (e:Exception){
            e.printStackTrace()
            Resorces.Error(e,-1,e.message.toString())

        }

    }
}