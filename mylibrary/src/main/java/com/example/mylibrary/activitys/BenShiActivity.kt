package com.example.mylibrary.activitys

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mylibrary.R
import com.example.mylibrary.databinding.ActivityRecylerViewBinding
import com.example.mylibrary.fragments.PostListFragment
import com.example.mylibrary.viewModel.PostListFragmentViewModel
import com.example.mylibrary.workers.SendEmailWorker
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration

@AndroidEntryPoint
class BenShiActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostListFragmentViewModel>()
    private lateinit var binding: ActivityRecylerViewBinding

    private val POST_FRAGMENT_TAG="POST_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecylerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

    }


    override fun onStop() {

        val sendEmailWorker= OneTimeWorkRequestBuilder<SendEmailWorker>().addTag("sendEami")
            .build()
        WorkManager.getInstance(applicationContext).enqueue(sendEmailWorker)
        super.onStop()
    }

}