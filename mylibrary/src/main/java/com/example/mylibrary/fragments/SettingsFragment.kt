package com.example.mylibrary.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mylibrary.R
import com.example.mylibrary.SharedPref
import com.example.mylibrary.Util
import com.example.mylibrary.databinding.FragmentSettingsBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding by viewBinding  (FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSaveEmail.setOnClickListener {
            if (Util.isValidEmail(binding.editTextEmail.text)){
                SharedPref.write(SharedPref.USER_EMAIL,binding.editTextEmail.text.toString())
                Toast.makeText(requireContext(),"Save",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(),"Enter valid email",Toast.LENGTH_LONG).show()

            }
        }

    }


}