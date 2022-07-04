package com.example.mylibrary.fragments

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.*
import com.example.mylibrary.adupters.PostRecyclerviewAdapter
import com.example.mylibrary.databinding.FragmentPostListBinding
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.viewModel.PostListFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostListFragment : BaseFragment(R.layout.fragment_post_list) {

    private val viewModel by viewModels<PostListFragmentViewModel> ()
    private val binding by viewBinding (FragmentPostListBinding::bind)

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var postApter: PostRecyclerviewAdapter? = null

    override fun initObserver() {
        super.initObserver()
        viewModel.postListLiveData.observe(this) {
            when (it) {
                is Resorces.Success -> {

                    viewModel.postListUiData = it.data

                    linearLayoutManager = LinearLayoutManager(requireActivity())
                    postApter = PostRecyclerviewAdapter(viewModel){
                        openDetailPage(viewModel.postListUiData[it])
                    }
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
                else -> {

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
                    binding.progress.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun openDetailPage(postUi: PostUi) {
        postUi.author?.address?.geo?.apply {
            this.lat="31.4573521"
            this.lng="74.3000763"
        }
       findNavController().navigate(R.id.action_postListFragment_to_detailFragment)

        activtyViewModel.dataPostListToDetailFragment.value=postUi
       }

}