package com.sohail.urjahub.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.sohail.urjahub.R

import com.sohail.urjahub.activity.Scanner

class Map : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    lateinit var btnScan: Button
    val guwahati = LatLng(26.1445, 91.7362)
    val gauhatiUniversity = LatLng(26.1540, 91.6630)
    val iitGuwahati = LatLng(26.1878, 91.6916)
    val cottonUniversity = LatLng(26.1874, 91.7467)
    val commerceCollege = LatLng(26.1801, 91.7756)
    val downtownUniversity = LatLng(26.2017, 91.8615)
    val royalGlobalUniversity = LatLng(26.1122, 91.7242)
    val ustm = LatLng(26.1032, 91.8464)

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.addMarker(MarkerOptions().position(guwahati).title("Marker in Guwahati"))
        googleMap.addMarker(MarkerOptions().position(gauhatiUniversity).title("Gauhati University"))
        googleMap.addMarker(MarkerOptions().position(iitGuwahati).title("IIT Guwahati"))
        googleMap.addMarker(MarkerOptions().position(cottonUniversity).title("Cotton University"))
        googleMap.addMarker(
            MarkerOptions().position(commerceCollege).title("Gauhati Commerce College")
        )
        googleMap.addMarker(
            MarkerOptions().position(downtownUniversity).title("Downtown University")
        )
        googleMap.addMarker(
            MarkerOptions().position(royalGlobalUniversity).title("Royal Global University")
        )
        googleMap.addMarker(
            MarkerOptions().position(ustm).title("University of Science and Technology, Meghalaya")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(guwahati, 15f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        btnScan = view.findViewById(R.id.btnScanQR)
        btnScan.setOnClickListener {
            val intentScan = Intent(activity as Context, Scanner::class.java)
            startActivity(intentScan)
        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Context)
        return view
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        mMap.addMarker(
            MarkerOptions().position(guwahati).title("Marker in Guwahati")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(gauhatiUniversity).title("Gauhati University")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(iitGuwahati).title("IIT Guwahati")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(cottonUniversity).title("Cotton University")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(commerceCollege).title("Gauhati Commerce College")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(downtownUniversity).title("Downtown University")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(royalGlobalUniversity).title("Royal Global University")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.addMarker(
            MarkerOptions().position(ustm).title("University of Science and Technology, Meghalaya")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    override fun onMarkerClick(p0: Marker) = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                activity as Context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

    }
}

