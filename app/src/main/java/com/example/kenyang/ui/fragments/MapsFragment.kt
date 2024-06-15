package com.example.kenyang.ui.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kenyang.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment() : Fragment() {

    private var lat =  0.0
    private var lon = 0.0

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val focus = LatLng(lat, lon)
        googleMap.addMarker(MarkerOptions().position(focus).title("Lokasi"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(focus))
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
            CameraPosition.builder().target(focus).zoom(15f).tilt(45f).build()
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        fun newInstance(latitude: Double, longitude: Double): MapsFragment {
            val fragment = MapsFragment()
            fragment.lat = latitude
            fragment.lon = longitude
            return fragment
        }
    }
}