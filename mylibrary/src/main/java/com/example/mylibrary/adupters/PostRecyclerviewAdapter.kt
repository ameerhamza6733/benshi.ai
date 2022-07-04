package com.example.mylibrary.adupters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mylibrary.databinding.EachRowPostBinding
import com.example.mylibrary.model.ui.PostUi
import com.example.mylibrary.viewModel.PostListFragmentViewModel

class PostRecyclerviewAdapter(val postListUiData: List<PostUi>,  private val onItemClicked: (Int) -> Unit, private val onBind:(Int)-> Unit) :
    RecyclerView.Adapter<PostRecyclerviewAdapter.ViewHolder>() {
    private val TAG="PostRecyclerviewAdapter"
   inner class ViewHolder(val binding: EachRowPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = binding.apply {
            val post=postListUiData[adapterPosition]

            post.postImageUrl?.let {
                Glide.with(binding.postImage).asBitmap().load(it).into(binding.postImage)
            }
            post.author?.name?.let {
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

        onBind(position)
    }

    override fun getItemId(position: Int): Long {
        return postListUiData[position].id.toLong()
    }

    override fun getItemCount(): Int {
        return postListUiData.size
    }
}