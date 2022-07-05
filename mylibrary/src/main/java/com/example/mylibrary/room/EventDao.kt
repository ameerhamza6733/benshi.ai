package com.example.mylibrary.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mylibrary.model.local.Events

@Dao
interface EventDao {

    @Insert
    suspend fun saveEvent(eventsJsonString: Events)

    @Query("SELECT * FROM Events")
    suspend fun getEvent():List<Events>
}