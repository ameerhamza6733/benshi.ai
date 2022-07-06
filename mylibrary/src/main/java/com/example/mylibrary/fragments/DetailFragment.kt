package com.example.mylibrary.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mylibrary.Log
import com.example.mylibrary.R
import com.example.mylibrary.Resorces
import com.example.mylibrary.Util
import com.example.mylibrary.adupters.PostDetailAdupter
import com.example.mylibrary.databinding.FragmentDetailBinding
import com.example.mylibrary.model.EventByUser
import com.example.mylibrary.model.Meta
import com.example.mylibrary.model.request.CommentPostRequest
import com.example.mylibrary.model.request.UserDetailRequest
import com.example.mylibrary.repository.EventRepo
import com.example.mylibrary.viewModel.DetailFragmentViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail), OnMapReadyCallback {

    @Inject
    lateinit var eventRepo:EventRepo
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailFragmentViewModel> ()
    private var postDetailAdupter:PostDetailAdupter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

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
                val envent =EventByUser(EventByUser.TEST_APP_ID,postDetail.postUi.id.toString(),EventByUser.ACTION_VIEW,Util.id(requireContext().applicationContext).toString(),
                    Meta(System.currentTimeMillis(),Meta.TEST_GPS_LOCATION)
                )
                postDetailAdupter=PostDetailAdupter(postDetail)
                eventRepo.saveEvent(events = envent)
                binding.recyclerViewPostDetail.layoutManager=LinearLayoutManager(requireActivity())
                binding.recyclerViewPostDetail.adapter=postDetailAdupter

                Log("${postDetail.postUi.author?.name}")
                //incase we fail to download comment list in previous screen then force to downlaod here
                if (postDetail.commentList==null || postDetail.commentList?.isEmpty()==true){
                    viewModel.getPostComments(CommentPostRequest(postDetail.postUi.id))
                }

                if (postDetail.postUi.author==null){
                    viewModel.getUserDetail(UserDetailRequest(postDetail.postUi.userId))
                }
            }
        }

        viewModel.userDetailResponseLiveData.observe(this,{
            when(it){
                is Resorces.Success->{
                    binding.progress.visibility=View.INVISIBLE
                    postDetailAdupter?.postDetailUi?.postUi?.author=it.data
                    postDetailAdupter?.notifyDataSetChanged()
                }
                is Resorces.Error->{
                    binding.progress.visibility=View.INVISIBLE
                    Toast.makeText(activity!!,"error while loaidng user data",Toast.LENGTH_LONG).show()
                }
                is Resorces.Loading->{
                    binding.progress.visibility=View.VISIBLE
                }
            }
        })

        viewModel.liveDataCommentReponse.observe(this,{
            when(it){
                is Resorces.Success->{
                    binding.progress.visibility=View.INVISIBLE
                    postDetailAdupter?.postDetailUi?.commentList=it.data.postCommentList
                    postDetailAdupter?.notifyDataSetChanged()
                }
                is Resorces.Error->{
                    binding.progress.visibility=View.INVISIBLE
                  Toast.makeText(activity!!,"error while loaidng comments",Toast.LENGTH_LONG).show()
                }
                is Resorces.Loading->{
                    binding.progress.visibility=View.VISIBLE
                }
            }
        })
    }



    override fun onMapReady(p0: GoogleMap) {

    }
}