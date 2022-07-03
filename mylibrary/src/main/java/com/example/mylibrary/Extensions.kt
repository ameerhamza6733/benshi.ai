package com.example.mylibrary

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

fun Activity.Log(tag:String){
    android.util.Log.d(javaClass.simpleName,tag)
}

fun ViewModel.Log(tag: String){
    android.util.Log.d(javaClass.simpleName,tag)

}

fun Fragment.Log(tag: String){
    android.util.Log.d(javaClass.simpleName,tag)
}