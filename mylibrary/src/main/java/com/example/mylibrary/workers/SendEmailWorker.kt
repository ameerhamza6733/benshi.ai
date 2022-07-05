package com.example.mylibrary.workers

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.example.mylibrary.SharedPref
import com.example.mylibrary.activitys.OpenEmailActivity
import com.example.mylibrary.repository.EventRepo
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SendEmailWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventRepo: EventRepo
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
       val eventsStringList=eventRepo.getEevnets().map {
           it.jsonString
       }
        val eventJsonString=Gson().toJson(eventsStringList)
        val email=SharedPref.read(SharedPref.USER_EMAIL,"")

        if (email.isNullOrEmpty()){
            Toast.makeText(applicationContext,"Event will not send email null",Toast.LENGTH_LONG).show()
            return Result.failure()
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            val intetn=Intent(applicationContext,OpenEmailActivity::class.java)
            intetn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intetn.putExtra("eventJsonString",eventJsonString)
            intetn.putExtra("email",email)
            applicationContext.startActivity(intetn)
        }, 0)




return Result.success()
    }

}
