package com.example.mylibrary.fragments

import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.mylibrary.Log
import com.example.mylibrary.R
import com.example.mylibrary.databinding.FragmentDetailBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail), OnMapReadyCallback {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    override fun initViews() {
        super.initViews()

        binding.map.setTileSource(TileSourceFactory.MAPNIK)


//        val mapFragment = getChildFragmentManager()
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

    }

    override fun initObserver() {
        super.initObserver()

        activtyViewModel.dataPostListToDetailFragment.observe(viewLifecycleOwner) {
            it?.let { post ->
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
                    Log("${it.lat} ${it.lng}")
                    val startPoint = GeoPoint(it.lat?.toDouble()?:0.0,it.lng?.toDouble()?:0.0)
                    val mapController =  binding.map.controller
                    mapController.setZoom(12.5)
                    mapController.setCenter(startPoint);
                    addMakerToMap(GeoPoint(it.lat?.toDouble()?:0.0,it.lng?.toDouble()?:0.0))
                }
            }
        }
    }

    private fun addMakerToMap(geoPoint: GeoPoint){
      val   marker = Marker(binding.map)
        marker.position = geoPoint
        marker.icon = ContextCompat.getDrawable(requireContext(), org.osmdroid.library.R.drawable.ic_menu_mylocation)
        marker.title = "${geoPoint.latitude},${geoPoint.longitude}"
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
        binding.map.overlays.add(marker)
        binding.map.invalidate()
    }

    override fun onMapReady(p0: GoogleMap) {

    }
}