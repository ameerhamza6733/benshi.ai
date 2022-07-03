package com.example.mylibrary

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollLis(layoutManager: LinearLayoutManager,viewModel: RecyclerviewActivityViewModel){
    this.addOnScrollListener(object :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                // Do something
            } else if (newState ==  RecyclerView.SCROLL_STATE_IDLE) {
                // Do something
                viewModel.onRecyclerViewStateIdeal(layoutManager.findFirstCompletelyVisibleItemPosition())
                viewModel.onRecyclerViewStateIdeal(layoutManager.findLastCompletelyVisibleItemPosition())
                viewModel.onRecyclerViewStateIdeal(layoutManager.findFirstVisibleItemPosition())
                viewModel.onRecyclerViewStateIdeal(layoutManager.findLastVisibleItemPosition())

            } else {
                // Do something
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

        }
    })
}