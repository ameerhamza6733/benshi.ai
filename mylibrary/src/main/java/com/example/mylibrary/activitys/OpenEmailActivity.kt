package com.example.mylibrary.activitys

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.ListenableWorker

class OpenEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email=intent.getStringExtra("email")
        val eventJsonString=intent.getStringExtra("eventJsonString")

        val emailIntent = Intent(Intent.ACTION_SENDTO)
            .setData( Uri.Builder().scheme("mailto").build())
            .putExtra(Intent.EXTRA_EMAIL,  arrayOf( "<${email}>" ))
            .putExtra(
                Intent.EXTRA_SUBJECT, "events"
            )
            .putExtra(Intent.EXTRA_TEXT, eventJsonString)
        try {
            emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext. startActivity(
                Intent.createChooser(
                    emailIntent,
                    "choose"
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }catch (E:Exception){
            E.printStackTrace()
            Toast.makeText(applicationContext,"Unable to star email", Toast.LENGTH_LONG).show()

        }
        finish()
    }
}