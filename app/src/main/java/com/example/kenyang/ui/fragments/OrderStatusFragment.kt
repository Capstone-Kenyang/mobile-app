package com.example.kenyang.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kenyang.databinding.FragmentOrderStatusBinding

class OrderStatusFragment : Fragment() {

    private lateinit var binding: FragmentOrderStatusBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvStatus.text = arguments?.getString(ARG_STRING)
    }

    companion object {

        private const val ARG_STRING = "message"
        @JvmStatic
        fun newInstance(message: String) =
            OrderStatusFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_STRING, message)
                }
            }
    }
}