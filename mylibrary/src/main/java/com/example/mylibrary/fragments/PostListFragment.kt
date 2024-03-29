package com.example.mylibrary.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.*
import com.example.mylibrary.adupters.PostRecyclerviewAdapter
import com.example.mylibrary.databinding.FragmentPostListBinding
import com.example.mylibrary.model.EventByUser
import com.example.mylibrary.model.Meta
import com.example.mylibrary.model.local.Events
import com.example.mylibrary.model.ui.PostDetailUi
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.repository.EventRepo
import com.example.mylibrary.viewModel.PostListFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostListFragment  : BaseFragment(R.layout.fragment_post_list) {

    @Inject
    lateinit var myRepository: EventRepo

    private val viewModel by viewModels<PostListFragmentViewModel>()
    private val binding by viewBinding(FragmentPostListBinding::bind)

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var postApter: PostRecyclerviewAdapter? = null

    override fun initViews() {
        super.initViews()
        val scrollEvent=EventByUser(EventByUser.TEST_APP_ID,"PostListFragment",EventByUser.ACTION_VIEW,EventByUser.TEST_USER_DEVICE_ID,
            Meta(System.currentTimeMillis(),Meta.TEST_GPS_LOCATION)
        )
        myRepository.saveEvent(scrollEvent)
        binding.btSettings.setOnClickListener {
            findNavController().navigate(R.id.action_postListFragment_to_settingsFragment)
        }
    }

    override fun initObserver() {
        super.initObserver()


        viewModel.postListLiveData.observe(this) {
            when (it) {
                is Resorces.Success -> {
                    Log("post list live data success")
                    viewModel.postListUiData =
                        it.data // we can skip  viewModel.postListUiData =it.data if we don't need to modify list view date but in this case the image author name was added to list

                    linearLayoutManager = LinearLayoutManager(requireActivity())
                    postApter = PostRecyclerviewAdapter(viewModel.postListUiData, onBind = { pos ->
                        viewModel.onRecyclerViewBind(pos)
                    }, onItemClicked = {
                        openDetailPage(viewModel.postListUiData[it],it)
                    })
                    postApter?.setHasStableIds(true)
                    binding.recyclerViewPost.disableItemAnimator()
                    binding.recyclerViewPost.itemAnimator?.changeDuration=0
                    binding.recyclerViewPost.layoutManager = linearLayoutManager
                    binding.recyclerViewPost.adapter = postApter
                    binding.recyclerViewPost.scrollLis(linearLayoutManager, viewModel){
                       val scrollEvent=EventByUser(EventByUser.TEST_APP_ID,"-1","scroll",EventByUser.TEST_USER_DEVICE_ID,
                           Meta(System.currentTimeMillis(),Meta.TEST_GPS_LOCATION)
                       )
                        myRepository.saveEvent(scrollEvent)
                    }
                    binding.recyclerViewPost.scrollToPosition(viewModel.currentItemClick)

                    binding.progress.visibility = View.INVISIBLE
                }
                is Resorces.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is Resorces.Error -> {
                    binding.progress.visibility = View.INVISIBLE
                    Log(it.errorMessage)

                    if (it.error?.isInternetError() == true) {
                        Snackbar.make(binding.progress, "network error", Snackbar.LENGTH_INDEFINITE)
                            .setAction("tryAgain") {
                                viewModel.getPosts()
                            }.show()
                    } else {
                        Snackbar.make(binding.progress, "${it.errorMessage}", Snackbar.LENGTH_LONG)
                            .show()
                    }


                    Log("error while loading user detail ${it.errorMessage.toString()}")

                }
            }
        }

        viewModel.postImageResponseLiveData.observe(this) {
            when (it) {
                is Resorces.Success -> {
                    postApter?.notifyItemChanged(it.data.currentPostionInRecylerView)
                }
                is Resorces.Error->{

                }
                else -> {

                }
            }
        }

        viewModel.liveDataCommentReponse.observe(this){
            when(it){
                is Resorces.Success->{
                    postApter?.notifyItemChanged(it.data.currentPositionInRecyclerView)
                }
                is Resorces.Loading->{

                }
                is Resorces.Error->{
                    //fail to load comment count , will retry again once xyz post visible again
                }

            }
        }

        viewModel.userDetailResponseLiveData.observe(this) {
            when (it) {
                is Resorces.Success -> {
                    postApter!!.notifyItemChanged(it.data.currentPositionInRecyclerView)
                }
                is Resorces.Loading -> {

                }
                is Resorces.Error -> {
                    //fail to load user data  , will retry again once xyz post visible again

                    Log(" error user not ${it.errorMessage}")
                }
            }
        }
    }

    private fun openDetailPage(postUi: PostUi,itemClick:Int) {
        viewModel.currentItemClick=itemClick
        findNavController().navigate(R.id.action_postListFragment_to_detailFragment)
        activtyViewModel.dataPostListToDetailFragment.value = PostDetailUi(postUi,viewModel.postCommentHashMap[postUi.id])
    }

}