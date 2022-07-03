package com.example.mylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.databinding.ActivityRecylerViewBinding

class RecyclerViewActivity : AppCompatActivity() {

    private val viewModel by viewModels<RecyclerviewActivityViewModel> ()
    private lateinit var binding:ActivityRecylerViewBinding
    private lateinit var linearLayoutManager:LinearLayoutManager
    private  var postApter:PostRecyclerviewAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecylerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObserver()

        viewModel.getPosts()

    }

    private fun initViews(){

    }
    private fun initObserver(){
        viewModel.postListLiveData.observe(this) {
            when (it) {
                is Resorces.Success -> {

                    viewModel.postListUiData = it.data

                    linearLayoutManager=LinearLayoutManager(this)
                    postApter= PostRecyclerviewAdapter(viewModel)
                    binding.recyclerViewPost.layoutManager = linearLayoutManager
                    binding.recyclerViewPost.adapter = postApter
                    binding.recyclerViewPost.scrollLis(linearLayoutManager,viewModel)
                    binding.progress.visibility= View.INVISIBLE
                }
                is Resorces.Loading->{
                    binding.progress.visibility= View.VISIBLE
                }
                is Resorces.Error->{

                }
            }
        }

        viewModel.userDetailResponseLiveData.observe(this,{
            when(it){
               is Resorces.Success->{
                   postApter!!.notifyItemChanged(it.data.currentPositionInRecyclerView)
               }
            }
        })
    }
}