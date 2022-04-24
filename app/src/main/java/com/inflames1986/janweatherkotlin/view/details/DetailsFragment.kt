package com.inflames1986.janweatherkotlin.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.databinding.FragmentDetailsBinding
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.utils.KEY_BUNDLE_WEATHER
import com.inflames1986.janweatherkotlin.viewmodel.DetailsState
import com.inflames1986.janweatherkotlin.viewmodel.DetailsViewModel


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, object : Observer<DetailsState> {
            override fun onChanged(t: DetailsState) {
                renderData(t)
            }
        })
        arguments?.getParcelable<Weather>(KEY_BUNDLE_WEATHER)?.let {
            viewModel.getWeather(it.city)
        }
    }

    private fun renderData(detailsState: DetailsState) {
        when (detailsState) {
            is
            DetailsState.Error -> {
                //TODO: отобразить ошибки
            }
            DetailsState.Loading -> {
                //TODO: отобразить загрузку
            }
            is DetailsState.Success -> {
                val weather = detailsState.weather
                with(binding) {
                    progressBar.visibility = View.GONE
                    cityName.text = weather.city.name
                    temperatureLabel.text = getString(R.string.temperature)
                    temperatureValue.text = weather.temperature.toString()
                    feelsLikeLabel.text = getString(R.string.feelsLikeLabelText)
                    feelsLikeValue.text = weather.feelsLike.toString()
                    cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
                    Snackbar.make(mainView, "Получилось", Snackbar.LENGTH_LONG)
                        .show()

                    Glide.with(requireContext())
                        .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                        .into(headerCityIcon)

//                    Picasso.get()?.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
//                         ?.into(headerIcon)

//                    headerCityIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
//                    icon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg")
                }
            }
        }
    }

    companion object {

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}


