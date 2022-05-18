package com.inflames1986.janweatherkotlin.view.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.databinding.FragmentWeatherListBinding
import com.inflames1986.janweatherkotlin.model.entities.City
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.utils.KEY_BUNDLE_WEATHER
import com.inflames1986.janweatherkotlin.view.details.DetailsFragment
import com.inflames1986.janweatherkotlin.viewmodel.AppState
import com.inflames1986.janweatherkotlin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class WeatherListFragment : Fragment(), OnItemListClickListener {

    private val viewModel: MainViewModel by viewModel()
    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!


    private val adapter = WeatherListFragmentAdapter(this)

    override fun onItemListClick(weather: Weather) {
        val manager = activity?.supportFragmentManager
        manager?.let {
            val bundle = Bundle()
            bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
            manager.beginTransaction()
                .replace(R.id.container, DetailsFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }
    }

    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isDataSetRus = true

        binding.fragmentWetherListRecyclerView.adapter = adapter
        binding.fragmentWeatherListButton.setOnClickListener {

            setupFabLocation()
            changeWeatherDataSet()

        }
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun setupFabLocation() {
        binding.mainFragmentFABLocation.setOnClickListener {
            checkPermission()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            explain()
        } else {
            mRequestPermission()
        }
    }

    private fun explain() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.dialog_rationale_title))
            .setMessage(resources.getString(R.string.dialog_rationale_message))
            .setPositiveButton(resources.getString(R.string.dialog_rationale_give_access)) { _, _ ->
                mRequestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private val REQUEST_CODE = 997
    private fun mRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            for (i in permissions.indices) {
                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    explain()
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val timeStump = System.currentTimeMillis()
        Thread {
            val addressText =
                geocoder.getFromLocation(location.latitude, location.longitude, 1000000)[0]
                    .getAddressLine(0)
            requireActivity().runOnUiThread {
                showAddressDialog(addressText, location)
            }
        }.start()
        Log.d("@@@", " прошло ${System.currentTimeMillis() - timeStump}")
    }

    private val locationListenerTime = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("@@@", location.toString())
            getAddressByLocation(location)
        }
    }

    private val locationListenerDistance = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("@@@", location.toString())
            getAddressByLocation(location)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        context?.let {
            val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val providerGPS =
                    locationManager.getProvider(LocationManager.GPS_PROVIDER) // можно использовать BestProvider

                providerGPS?.let {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        100f,
                        locationListenerDistance
                    )
                }
            }
        }
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    onItemListClick(
                        Weather(
                            City(
                                address,
                                location.latitude,
                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }


    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.fragmentWeatherListButton.setImageResource(R.drawable.ic_baseline_blur_circular_24)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            binding.fragmentWeatherListButton.setImageResource(R.drawable.ic_baseline_blur_circular_24)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.fragmentWeatherListLoadingLayout.visibility = View.GONE
                binding.fragmentWetherListRecyclerView.isVisible = true
                adapter.setWeather(appState.weatherList)
                binding.root.myOwnView("Success", Snackbar.LENGTH_LONG)
            }
            is AppState.Loading -> {
                binding.fragmentWeatherListLoadingLayout.visibility = View.VISIBLE
                binding.fragmentWetherListRecyclerView.isVisible = false
            }
            is AppState.Error -> {
                binding.fragmentWeatherListLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.fragmentWeatherListButton,
                        getString(R.string.error),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }

        }
    }

    fun View.myOwnView(stringRes: String, length: Int) {
        Snackbar.make(
            this,
            stringRes,
            length
        )
            .show()
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = WeatherListFragment()
    }
}


