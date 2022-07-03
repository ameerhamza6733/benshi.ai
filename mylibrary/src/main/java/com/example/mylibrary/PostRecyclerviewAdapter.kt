package com.example.mylibrary

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mylibrary.databinding.EachRowPostBinding

class PostRecyclerviewAdapter(val viewModel: RecyclerviewActivityViewModel) :
    RecyclerView.Adapter<PostRecyclerviewAdapter.ViewHolder>() {
    private val TAG="PostRecyclerviewAdapter"
   inner class ViewHolder(val binding: EachRowPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = binding.apply {
            val post=viewModel.postListUiData[adapterPosition]
            post.author?.username?.let {
                binding.postAuthor.text=it
            }
            binding.postTitle.text=post.title
            binding.postDescription.text=post.body
            Log.d(TAG,"auther name ${post.author?.username}")

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostRecyclerviewAdapter.ViewHolder {
        val binding =
            EachRowPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostRecyclerviewAdapter.ViewHolder, position: Int) {
        holder.bind()


    }

    override fun getItemCount(): Int {
        return viewModel.postListUiData.size
    }
}