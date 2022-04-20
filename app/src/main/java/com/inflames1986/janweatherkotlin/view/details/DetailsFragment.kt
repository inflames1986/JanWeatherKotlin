package com.inflames1986.janweatherkotlin.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.inflames1986.janweatherkotlin.databinding.FragmentDetailsBinding
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.OnServerResponse
import com.inflames1986.janweatherkotlin.model.repository.OnServerResponseListener
import com.inflames1986.janweatherkotlin.dto.WeatherDTO
import com.inflames1986.janweatherkotlin.model.repository.WeatherLoader
import com.inflames1986.janweatherkotlin.services.DetailsService
import com.inflames1986.janweatherkotlin.utils.*
import com.inflames1986.janweatherkotlin.viewmodel.ResponseState


class DetailsFragment : Fragment(), OnServerResponse, OnServerResponseListener {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let {
                    onResponse(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var currentCityName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver,
            IntentFilter(KEY_WAVE_SERVICE_BROADCAST)
        )

        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.name

            requireActivity().startService(
                Intent(
                    requireContext(),
                    DetailsService::class.java
                ).apply {
                    putExtra(KEY_BUNDLE_LAT, it.city.lat)
                    putExtra(KEY_BUNDLE_LON, it.city.lon)
                })
        }
    }

    private fun renderData(weatherDTO: WeatherDTO) {
        with(binding) {
            cityName.text = currentCityName
            temperatureLabel.text = "Температура воздуха:"
            temperatureValue.text = weatherDTO.factDTO.temperature.toString()
            feelsLikeLabel.text = "Чувствуется как:"
            feelsLikeValue.text = weatherDTO.factDTO.feelsLike.toString()
            cityCoordinates.text = "${weatherDTO.infoDTO.lat} ${weatherDTO.infoDTO.lon}"
        }
    }


    companion object {

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResponse(weatherDTO: WeatherDTO) {
        renderData(weatherDTO)
    }

    override fun onError(error: ResponseState) {
            when (error) {
                is ResponseState.Error1 -> {
                    Snackbar.make(binding.cityName, "Error serverside :(", Snackbar.LENGTH_LONG).show()
                }
                is ResponseState.Error2 -> {
                    Snackbar.make(binding.cityName, "Error clientside :(", Snackbar.LENGTH_LONG).show()
                }
                is ResponseState.Error3 -> {
                    Snackbar.make(binding.cityName, "Response Ok!!!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }


