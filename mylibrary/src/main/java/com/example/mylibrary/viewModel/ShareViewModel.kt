package com.example.mylibrary.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylibrary.model.ui.PostDetailUi
import com.example.mylibrary.model.ui.PostUi

class ShareViewModel :ViewModel() {
     val dataPostListToDetailFragment=MutableLiveData<PostDetailUi?>()
}