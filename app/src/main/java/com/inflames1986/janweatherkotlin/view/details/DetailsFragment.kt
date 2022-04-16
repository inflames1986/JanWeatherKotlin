package com.inflames1986.janweatherkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inflames1986.janweatherkotlin.databinding.FragmentDetailsBinding
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.model.repository.OnServerResponse
import com.inflames1986.janweatherkotlin.model.repository.WeatherDTO
import com.inflames1986.janweatherkotlin.model.repository.WeatherLoader
import com.inflames1986.janweatherkotlin.utils.KEY_BUNDLE_WEATHER


class DetailsFragment : Fragment(), OnServerResponse {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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

        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            currentCityName = it.city.name
            Thread {
                WeatherLoader(this@DetailsFragment).loadWeather(it.city.lat, it.city.lon)
            }.start()
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
}

