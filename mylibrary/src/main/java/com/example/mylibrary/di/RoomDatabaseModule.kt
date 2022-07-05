package com.example.mylibrary.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mylibrary.room.EventDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {
    @Singleton
    @Provides
    fun getDatabase( @ApplicationContext app: Context):EventDataBase{
      return  Room.databaseBuilder(
            app,
          EventDataBase::class.java,
            "EventDataBase"
        ).build()
    }

}