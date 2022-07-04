package com.example.mylibrary.adupters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mylibrary.databinding.EachRowPostBinding
import com.example.mylibrary.viewModel.PostListFragmentViewModel

class PostRecyclerviewAdapter(val viewModel: PostListFragmentViewModel,  private val onItemClicked: (Int) -> Unit) :
    RecyclerView.Adapter<PostRecyclerviewAdapter.ViewHolder>() {
    private val TAG="PostRecyclerviewAdapter"
   inner class ViewHolder(val binding: EachRowPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = binding.apply {
            val post=viewModel.postListUiData[adapterPosition]

            post.postImageUrl?.let {
                Glide.with(binding.postImage).asBitmap().load(it).into(binding.postImage)
            }
            post.author?.username?.let {
                binding.postAuthor.text=it
            }
            binding.postTitle.text=post.title
            binding.postDescription.text=post.body

            binding.root.setOnClickListener {
                 onItemClicked(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            EachRowPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        viewModel.onRecyclerViewBind(holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return viewModel.postListUiData.size
    }
}