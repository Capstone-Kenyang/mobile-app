package com.example.kenyang.ui.customUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.BottomSheetFragmentBinding
import com.example.kenyang.ui.fragments.MapsFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.Locale

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menu = it.getParcelable(ARG_MENU)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu?.let {

            val locale = Locale("id", "ID")
            val formattedPrice = NumberFormat.getNumberInstance(locale).format(it.price)

            binding.ivImageMenu.setImageResource(it.imageId)
            binding.tvMenu.text = it.menu
            binding.tvRestaurant.text = it.restaurant
            binding.tvAddress.text= it.restaurantAddress
            binding.orderButton.text= resources.getString(R.string.order_now, formattedPrice)
        }

        binding.tvClickableCheckLocation.setOnClickListener {
            navigateToMapsFragment(menu!!.lat, menu!!.lon)
        }
    }

    private fun navigateToMapsFragment(lat: Double, lon: Double) {
        val fragment = MapsFragment.newInstance(lat, lon)
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setDimAmount(0.7f)
        // Atur background overlay
        dialog?.window?.setBackgroundDrawableResource(R.drawable.overlay_background)

    }

    companion object {
        const val TAG = "BottomSheetFragment"
        private const val ARG_MENU = "menu"

        fun newInstance(menu: Menu) = BottomSheetFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_MENU, menu)
            }
        }
    }
}