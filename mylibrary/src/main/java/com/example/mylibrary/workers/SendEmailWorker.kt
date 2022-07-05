package com.example.mylibrary.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mylibrary.Log
import com.example.mylibrary.R

import com.example.mylibrary.SharedPref
import com.example.mylibrary.activitys.BenShiActivity
import com.example.mylibrary.activitys.OpenEmailActivity
import com.example.mylibrary.repository.EventRepo
import com.google.gson.Gson
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random


@HiltWorker
class SendEmailWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventRepo: EventRepo
) : CoroutineWorker(appContext, workerParams) {
    val NOTIFICATION_CHANNEL_ID = "event channel"
    override suspend fun doWork(): Result {

       val eventsStringList=eventRepo.getEevnets().map {
           it.jsonString
       }
        val eventJsonString=Gson().toJson(eventsStringList)
        val email=SharedPref.read(SharedPref.USER_EMAIL,"")

        if (email.isNullOrEmpty()){
          Log("email not found")
        }


        val notifyIntent = Intent(applicationContext, OpenEmailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("eventJsonString",eventJsonString)
            putExtra("email",email)
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            applicationContext, 0, notifyIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        var builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_email_24)
            .setContentText("click to send event")
            .setContentIntent(notifyPendingIntent)
            . setContentTitle("Events")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        createNotificationChannel()
        notificationManager.notify(Random(1).nextInt(), builder.build())




return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Events"
            val descriptionText = "Event  "
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
