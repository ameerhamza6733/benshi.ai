package com.example.mylibrary.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylibrary.model.commentReponse.CommentReponse
import com.example.mylibrary.model.userReponse.UserReponse
import com.example.mylibrary.Log
import com.example.mylibrary.Resorces
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.request.PostImageRequest
import com.example.mylibrary.model.request.UserDetailRequest
import com.example.mylibrary.model.ui.CommetReponseUI
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.repository.PostRepo
import com.example.mylibrary.repository.UserRepo
import com.example.mylibrary.room.EventDataBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListFragmentViewModel  @Inject constructor(private val postRepo:PostRepo,
                                                     private val userRepo:UserRepo,
private val datbase : EventDataBase): ViewModel() {

    private val _mutableAuthorLiveData:MutableLiveData<Resorces<UserReponse>> = MutableLiveData()
    val userDetailResponseLiveData:LiveData<Resorces<UserReponse>> =_mutableAuthorLiveData

   private val _mutablePostImageLiveData:MutableLiveData<Resorces<PostImageRequest>> = MutableLiveData()
    val postImageResponseLiveData:LiveData<Resorces<PostImageRequest>> =_mutablePostImageLiveData


    private val _mutablePostLiveData:MutableLiveData<Resorces<ArrayList<PostUi>>> = MutableLiveData()
    val postListLiveData:LiveData<Resorces<ArrayList<PostUi>>> = _mutablePostLiveData


    private val _mutableCommentsLiveData:MutableLiveData<Resorces<CommetReponseUI>> = MutableLiveData()
    val liveDataCommentReponse:LiveData<Resorces<CommetReponseUI>> = _mutableCommentsLiveData

    //this will be used in PostRecylerviewAdupter.class
    lateinit var postListUiData:ArrayList<PostUi>
    //this can be store in local database
    val userDetailHasMap=HashMap<Int, UserReponse>()
    val postCommentHashMap=HashMap<Int,List<CommentReponse>>()

    init {
        getPosts()
    }

    fun getPosts(){
        _mutablePostLiveData.value= Resorces.Loading()
        viewModelScope.launch (Dispatchers.IO) {
           val list= postRepo.getPost()
            _mutablePostLiveData.postValue(list)

        }

    }

    fun onRecyclerViewBind(itemPosition:Int){
        if (itemPosition>=0){
            if (postListUiData[itemPosition].author==null){
                getUserDetail(UserDetailRequest(userId = postListUiData[itemPosition].userId).also { it.currentPostionInRecylerView=itemPosition })
                 }
            if (postListUiData[itemPosition].postImageUrl==null){
                getImage(PostImageRequest(postListUiData[itemPosition].title).also { it.currentPostionInRecylerView=itemPosition })

            }
            if (postListUiData[itemPosition].comments==0){
             val commentPostRequest=  CommentPostRequest(postListUiData[itemPosition].id).also { it.currentPostionInRecylerView=itemPosition }
                getPostComments(commentPostRequest)
            }

        }
    }

   private fun getImage(postImageRequest: PostImageRequest){
        viewModelScope.launch (Dispatchers.Default){

            val imageUrl= postRepo.getPostImage(postImageRequest)
            val oldPost= postListUiData.get(postImageRequest.currentPostionInRecylerView)

            oldPost.postImageUrl=imageUrl
            postListUiData.set(postImageRequest.currentPostionInRecylerView,oldPost)

            _mutablePostImageLiveData.postValue(Resorces.Success(postImageRequest))
        }
    }

  private  fun getPostComments(commentPostRequest: CommentPostRequest){
        viewModelScope.launch (Dispatchers.IO){
            val reponse=postRepo.getComment(commentPostRequest)
            //we can do batter if we save this into room data base  in repo class and make changes in post table there
            when(reponse){
                is Resorces.Success ->{

                    val commentListReponse=  reponse.data.postCommentList


                    val post= postListUiData.get(commentPostRequest!!.currentPostionInRecylerView)
                    post.comments=commentListReponse.size
                    postListUiData.set(commentPostRequest!!.currentPostionInRecylerView,post)
                    postCommentHashMap.put(post.id,commentListReponse)
                    _mutableCommentsLiveData.postValue(reponse)
                }else->{
                _mutableCommentsLiveData.postValue(reponse)
                }
            }


        }
    }

   private fun getUserDetail(userDetailRequest: UserDetailRequest){
       Log("get user detail ${userDetailRequest.userId}")
           if (userDetailHasMap.containsKey(userDetailRequest.userId)){
               //we already have user detail
               _mutableAuthorLiveData.postValue(Resorces.Success(userDetailHasMap[userDetailRequest.userId]!!))

           }else{
               viewModelScope.launch (Dispatchers.IO){

                   val reponse= userRepo.getUserDetail(userDetailRequest)

                   when(reponse){
                       is Resorces.Success ->{

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