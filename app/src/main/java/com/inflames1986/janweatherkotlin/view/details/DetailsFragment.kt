package com.inflames1986.janweatherkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.databinding.FragmentDetailsBinding
import com.inflames1986.janweatherkotlin.model.entities.Weather


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if (weather != null) {
            val city = weather.city
            binding.cityName.text = city.city
            binding.cityCoordinates.text = "${getString(R.string.city_coordinates)} ${city.lat} ${city.lon}"
            binding.temperatureLabel.text = "Температура воздуха:"
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeLabel.text = "Чувствуется как:"
            binding.feelsLikeValue.text = weather.feelsLike.toString()
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

