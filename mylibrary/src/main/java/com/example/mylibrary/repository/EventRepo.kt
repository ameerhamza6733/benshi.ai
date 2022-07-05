package com.example.mylibrary.repository

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.mylibrary.model.EventByUser
import com.example.mylibrary.model.local.Events
import com.example.mylibrary.room.EventDataBase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventRepo @Inject constructor(private val database:EventDataBase) {

    fun saveEvent(events: EventByUser){
        GlobalScope.launch(Dispatchers.IO) {
            val jsonStrong =Gson().toJson(events)
                database.Dao().saveEvent(Events(jsonString = jsonStrong))

        }
    }

    suspend fun getEevnets(): List<Events> {
       return database.Dao().getEvent()
    }


}