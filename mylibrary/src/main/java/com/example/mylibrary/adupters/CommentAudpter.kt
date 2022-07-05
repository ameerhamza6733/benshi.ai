package com.example.mylibrary.adupters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.databinding.RowCommentBinding
import com.example.mylibrary.databinding.RowPostDetailBinding
import com.example.mylibrary.model.commentReponse.CommentReponse

class CommentAudpter(val list: List<CommentReponse>) :
    RecyclerView.Adapter<CommentAudpter.CommentViewHolder>() {

    inner class CommentViewHolder(val binding:RowCommentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind()=binding.apply {
            binding.comment.text=list[adapterPosition].body
            binding.commentUserEmail.text=list[adapterPosition].email
            binding.commentUserName.text=list[adapterPosition].name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding =
            RowCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
       holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}