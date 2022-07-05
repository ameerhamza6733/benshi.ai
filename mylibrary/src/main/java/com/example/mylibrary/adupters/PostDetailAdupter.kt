package com.example.mylibrary.adupters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mylibrary.Log
import com.example.mylibrary.databinding.EachRowPostBinding
import com.example.mylibrary.databinding.RowPostDetailBinding
import com.example.mylibrary.model.ui.PostDetailUi
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class PostDetailAdupter(val postDetailUi: PostDetailUi) : RecyclerView.Adapter<PostDetailAdupter.PostDetailViewHolder>() {
    inner class PostDetailViewHolder(val binding: RowPostDetailBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind()= binding.apply {
            postDetailUi.postUi.let { post ->
                binding.postDescription.text = post.body
                binding.postAuthor.text = post.author?.name
                binding.postAuthorEmail.text = post.author?.email
                binding.postAuthorPhoneNumber.text = post.author?.phone
                binding.postTitle.text = post.title
                binding.postAuthorWebsite.text=post.author?.website
                Glide.with(binding.postImage).asBitmap().load(
                    post.postImageUrl
                ).into(binding.postImage)
                post.author?.address?.geo?.let {

                    val startPoint = GeoPoint(it.lat?.toDouble()?:0.0,it.lng?.toDouble()?:0.0)
                    val mapController =  binding.map.controller
                    mapController.setZoom(12.5)
                    mapController.setCenter(startPoint);
                    addMakerToMap(GeoPoint(it.lat?.toDouble()?:0.0,it.lng?.toDouble()?:0.0))
                }


            }

            postDetailUi.commentList?.let {
                binding.commentRecylerView.layoutManager=LinearLayoutManager(binding.root.context)
                binding.commentRecylerView.adapter=CommentAudpter(it)
            }
        }

        private fun addMakerToMap(geoPoint: GeoPoint){
            binding.map.setTileSource(TileSourceFactory.MAPNIK)
            val   marker = Marker(binding.map)
            marker.position = geoPoint
            marker.icon = ContextCompat.getDrawable(binding.root.context, org.osmdroid.library.R.drawable.ic_menu_mylocation)
            marker.title = "${geoPoint.latitude},${geoPoint.longitude}"
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            binding.map.overlays.add(marker)
            binding.map.invalidate()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
        val binding =
            RowPostDetailBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        return PostDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 1
    }
}