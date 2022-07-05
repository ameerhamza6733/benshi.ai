package com.example.mylibrary.fragments

import android.preference.PreferenceManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mylibrary.Log
import com.example.mylibrary.R
import com.example.mylibrary.adupters.PostDetailAdupter
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




//        val mapFragment = getChildFragmentManager()
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

    }

    override fun initObserver() {
        super.initObserver()

        activtyViewModel.dataPostListToDetailFragment.observe(viewLifecycleOwner) {
            it?.let {postDetail->
                binding.recyclerViewPostDetail.layoutManager=LinearLayoutManager(requireActivity())
                binding.recyclerViewPostDetail.adapter=PostDetailAdupter(postDetail)
            }
        }
    }



    override fun onMapReady(p0: GoogleMap) {

    }
}