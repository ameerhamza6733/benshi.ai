package com.example.mylibrary.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.*
import com.example.mylibrary.adupters.PostRecyclerviewAdapter
import com.example.mylibrary.databinding.FragmentPostListBinding
import com.example.mylibrary.model.ui.PostDetailUi
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.viewModel.PostListFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment(R.layout.fragment_post_list) {

    private val viewModel by viewModels<PostListFragmentViewModel>()
    private val binding by viewBinding(FragmentPostListBinding::bind)

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var postApter: PostRecyclerviewAdapter? = null

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
                    binding.recyclerViewPost.itemAnimator?.changeDuration=0
                    binding.recyclerViewPost.layoutManager = linearLayoutManager
                    binding.recyclerViewPost.adapter = postApter
                    binding.recyclerViewPost.scrollLis(linearLayoutManager, viewModel)
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
                    Log("")
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
                    Log(" error user not ${it.errorMessage}")
                }
            }
        }
    }

    private fun openDetailPage(postUi: PostUi,itemClick:Int) {

        findNavController().navigate(R.id.action_postListFragment_to_detailFragment)
        activtyViewModel.dataPostListToDetailFragment.value = PostDetailUi(postUi,viewModel.postCommentHashMap[postUi.id])
    }

}