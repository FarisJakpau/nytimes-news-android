package com.faris.newsapp.ui.home

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.faris.newsapp.R
import com.faris.newsapp.databinding.FragmentHomeBinding
import com.faris.newsapp.models.PopularMenu
import com.faris.newsapp.models.SearchMenu
import com.faris.newsapp.utils.isPermissionGranted

class HomeFragment: Fragment(R.layout.fragment_home), HomeMenuAdapter.Listener, LocationListener{

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get()= _binding!!

    private val popularAdapter: HomeMenuAdapter<PopularMenu> by lazy {
        HomeMenuAdapter(this)
    }

    private val searchAdapter: HomeMenuAdapter<SearchMenu> by lazy {
        HomeMenuAdapter(this)
    }

    private lateinit var locationManager: LocationManager
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                binding.locationTextView.text = context?.getString(R.string.location_permission_denied)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        locationManager = this.context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        with(binding) {
            toolbar.textView.text = "NYT"

            popularAdapter.items = PopularMenu.values().toList()
            searchAdapter.items = SearchMenu.values().toList()

            popularRecyclerView.adapter = popularAdapter
            searchRecyclerView.adapter = searchAdapter
        }

        checkLocationPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun menuSelected(menu: Any) {
        when(menu) {
            is PopularMenu -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToArticlesFragment(
                    articlesType =  menu,
                ))
            }
            is SearchMenu -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
            }
        }
    }

    private fun getLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5f, this)
    }

    private fun checkLocationPermission() {
        when {
            this.requireContext().isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                getLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                binding.locationTextView.text = context?.getString(R.string.location_permission_rationale)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        binding.locationTextView.text = "Location latitude: ${String.format("%.2f", location.latitude)} longitude: ${String.format("%.2f", location.longitude)}"
    }

    override fun onProviderEnabled(provider: String) {
        getLocation()
    }

    override fun onProviderDisabled(provider: String) {
        binding.locationTextView.text = context?.getString(R.string.please_enable_location)
    }

    override fun onResume() {
        super.onResume()
        checkLocationPermission()
    }

    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(this)
    }
}