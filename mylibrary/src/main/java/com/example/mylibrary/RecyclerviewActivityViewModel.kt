package com.example.mylibrary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.UserReponse
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.request.PostImageRequest
import com.example.mylibrary.model.request.UserDetailRequest
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.repository.PostRepo
import com.example.mylibrary.repository.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class RecyclerviewActivityViewModel : ViewModel() {

    private val postRepo= PostRepo()
    private val userRepo=UserRepo()

    private val _mutableAuthorLiveData:MutableLiveData<Resorces<UserReponse>> = MutableLiveData()
    val userDetailResponseLiveData:LiveData<Resorces<UserReponse>> =_mutableAuthorLiveData

    private val _mutablePostLiveData:MutableLiveData<Resorces<ArrayList<PostUi>>> = MutableLiveData()
    val postListLiveData:LiveData<Resorces<ArrayList<PostUi>>> = _mutablePostLiveData

    //this will be used in PostRecylerviewAdupter.class
    lateinit var postListUiData:ArrayList<PostUi>
    //this can be store in local database
    private val userDetailHasMap=HashMap<Int,UserReponse>()

    fun getPosts(){
        _mutablePostLiveData.value=Resorces.Loading()
        viewModelScope.launch (Dispatchers.IO) {
           val list= postRepo.getPost()
            _mutablePostLiveData.postValue(list)

        }

    }

    fun onRecyclerViewStateIdeal(itemPosition:Int){
        if (itemPosition>=0){
            if (postListUiData[itemPosition].author==null){
                getUserDetail(UserDetailRequest(userId = postListUiData[itemPosition].userId).also { it.currentPostionInRecylerView=itemPosition })
                getPostComments(CommentPostRequest(postListUiData[itemPosition].id).also { it.currentPostionInRecylerView=itemPosition })
                getImage(PostImageRequest(postListUiData[itemPosition].title).also { it.currentPostionInRecylerView=itemPosition })
            }
        }
    }

    fun getImage(postImageRequest: PostImageRequest){
        viewModelScope.launch (Dispatchers.IO){
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val encodedhash: ByteArray = digest.digest(
                postImageRequest.postTitle.toByteArray()
            )
           postRepo.getPostImage(String(encodedhash))
        }
    }

    fun getPostComments(commentPostRequest: CommentPostRequest){
        viewModelScope.launch (Dispatchers.IO){
            val reponse=postRepo.getComment(commentPostRequest)
        }
    }

    fun getUserDetail(userDetailRequest: UserDetailRequest){

           if (userDetailHasMap.containsKey(userDetailRequest.userId)){
               //we already have user detail
           }else{
               viewModelScope.launch (Dispatchers.IO){

                   val reponse= userRepo.getUserDetail(userDetailRequest)

                   when(reponse){
                       is Resorces.Success->{

                           val userResponse=  reponse.data

                           val oldPost= postListUiData.get(userResponse!!.currentPositionInRecyclerView)
                           oldPost.author=userResponse
                           postListUiData.set(userResponse!!.currentPositionInRecyclerView,oldPost)
                           userDetailHasMap[userResponse.id!!]=userResponse

                           _mutableAuthorLiveData.postValue(Resorces.Success(userResponse))
                       }
                       else->{
                           _mutableAuthorLiveData.postValue(reponse)

                       }
                   }

               }
           }


    }
}