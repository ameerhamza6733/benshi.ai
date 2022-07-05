package com.example.mylibrary.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mylibrary.model.local.Events

@Database(entities = [Events::class], version = 2)
abstract class EventDataBase : RoomDatabase() {
    abstract fun Dao(): EventDao
}