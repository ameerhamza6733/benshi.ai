package com.example.mylibrary

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylibrary.viewModel.PostListFragmentViewModel

fun RecyclerView.scrollLis(layoutManager: LinearLayoutManager,viewModel: PostListFragmentViewModel,  onScrollEvent: (Int) -> Unit){
    this.addOnScrollListener(object :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                // Do something
            } else if (newState ==  RecyclerView.SCROLL_STATE_IDLE) {
                // Do something

            } else {
                // Do something
            }

            onScrollEvent(newState)

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

        }
    })
}