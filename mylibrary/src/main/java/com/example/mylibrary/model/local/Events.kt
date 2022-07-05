package com.example.mylibrary.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Events(
    @PrimaryKey (autoGenerate = true)
    val eventId:Int=0,
    val jsonString:String=""
) {

}