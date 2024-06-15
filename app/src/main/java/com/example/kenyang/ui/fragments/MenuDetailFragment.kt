package com.example.kenyang.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.kenyang.R
import com.example.kenyang.data.dataclass.Menu
import com.example.kenyang.data.dataclass.Order
import com.example.kenyang.databinding.BottomSheetFragmentBinding
import com.example.kenyang.factory.ViewModelFactory
import com.example.kenyang.ui.viewmodel.MenuDetailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MenuDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetFragmentBinding
    private var menu: Menu? = null

    private val menuDetailViewModel: MenuDetailViewModel by viewModels<MenuDetailViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            menu = it.getParcelable(ARG_MENU)
            Log.d("TAG", menu.toString())
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu?.let {

            val locale = Locale("id", "ID")
            val formattedPrice = NumberFormat.getNumberInstance(locale).format(it.price)

            val date = it.expireDate

            binding.ivImageMenu.setImageResource(it.imageId)
            binding.tvMenu.text = it.menu
            binding.tvRestaurant.text = it.restaurant
            binding.tvAddress.text= it.restaurantAddress
            binding.tvExpire.text = resources.getString(R.string.expire_warning, date)
            binding.orderButton.text= resources.getString(R.string.order_now, formattedPrice)
        }

        binding.tvClickableCheckLocation.setOnClickListener {
            dismiss()
            navigateToMapsFragment(menu!!.lat, menu!!.lon)
        }

        menuDetailViewModel.getAllOrderIds().observe(viewLifecycleOwner) { ordersIds ->
            binding.orderButton.setOnClickListener {
                val newUniqueId = generateId(ordersIds)
                val order = Order(newUniqueId, menu!!)

                viewLifecycleOwner.lifecycleScope.launch {
                    menuDetailViewModel.insertOrder(order)
                    Log.d("TAGorder", order.toString())
                }

                dismiss()
            }
        }



        binding.clickableTvDonation.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                menuDetailViewModel.getAllOrderIds().observe(viewLifecycleOwner) { ordersIds ->
                    val newUniqueId = generateId(ordersIds)

                    menuDetailViewModel.insertOrder(
                        Order(newUniqueId, menu!!, isDonation = true)
                    )
                }
            }
        }
    }

    private fun generateId(existingIds: List<String>): String {
        val randomPart1 = generateRandomString(3)
        val randomPart2 = generateRandomString(4)
        val newId = "$randomPart1-$randomPart2"

        return if (existingIds.contains(newId)) {
            generateId(existingIds)
        } else {
            newId
        }
    }

    private fun generateRandomString(length: Int): String {
        val chars = ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
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

        fun newInstance(menu: Menu) = MenuDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_MENU, menu)
            }
        }
    }
}