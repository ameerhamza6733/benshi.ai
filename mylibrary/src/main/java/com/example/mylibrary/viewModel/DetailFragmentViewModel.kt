package com.example.mylibrary.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.Resorces
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.request.UserDetailRequest
import com.example.mylibrary.model.ui.CommetReponseUI
import com.example.mylibrary.model.userReponse.UserReponse
import com.example.mylibrary.repository.PostRepo
import com.example.mylibrary.repository.UserRepo
import com.example.mylibrary.room.EventDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailFragmentViewModel @Inject constructor(private val postRepo: PostRepo,
                                                  private val userRepo: UserRepo,
                                                  private val datbase : EventDataBase):ViewModel() {

    private val _mutableCommentsLiveData: MutableLiveData<Resorces<CommetReponseUI>> = MutableLiveData()
    val liveDataCommentReponse: LiveData<Resorces<CommetReponseUI>> = _mutableCommentsLiveData


    private val _mutableAuthorLiveData:MutableLiveData<Resorces<UserReponse>> = MutableLiveData()
    val userDetailResponseLiveData:LiveData<Resorces<UserReponse>> =_mutableAuthorLiveData


    fun getUserDetail(userDetailRequest: UserDetailRequest){
        viewModelScope.launch (Dispatchers.IO){

            _mutableAuthorLiveData.postValue(userRepo.getUserDetail(userDetailRequest))
        }
    }

    fun getPostComments(commentPostRequest: CommentPostRequest){
          _mutableCommentsLiveData.postValue(Resorces.Loading())
        viewModelScope.launch (Dispatchers.IO){
            val reponse=postRepo.getComment(commentPostRequest)
            _mutableCommentsLiveData.postValue(reponse)
        }
    }
}