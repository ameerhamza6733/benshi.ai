package com.example.mylibrary.fragments

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mylibrary.viewModel.ShareViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment(@LayoutRes  contentLayoutId:Int) :Fragment(contentLayoutId) {
    open val  activtyViewModel by activityViewModels<ShareViewModel> ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        initViews()
    }

    open fun initViews() {

    }

    open fun initObserver(){

    }
}