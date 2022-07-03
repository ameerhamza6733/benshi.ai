package com.example.mylibrary

sealed class Resorces<T> {
   data class Success<T>(val data:T,val message:String=""):Resorces<T>()
    data class Error<T>(val error:Throwable?,val errorCode:Int=-1,var errorMessage:String=""):Resorces<T>()
    data class Loading<T>(val message:String=""):Resorces<T>()
}