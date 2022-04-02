package com.inflames1986.janweatherkotlin.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.databinding.FragmentWetherListBinding
import com.inflames1986.janweatherkotlin.model.entities.Weather
import com.inflames1986.janweatherkotlin.view.details.DetailsFragment
import com.inflames1986.janweatherkotlin.viewmodel.AppState
import com.inflames1986.janweatherkotlin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel() //делегирование инициализации viewModel в метод внутри Koin вместо onViewCreated
    private var _binding: FragmentWetherListBinding? = null
    private val binding get() = _binding!!

    private val adapter = WetherListFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }
    })
    private var isDataSetRus: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWetherListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentWetherListRecyclerView.adapter = adapter
        binding.fragmentWetherListButton.setOnClickListener { changeWeatherDataSet() }
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun changeWeatherDataSet() {
        if (isDataSetRus) {
            viewModel.getWeatherFromLocalSourceWorld()
            // binding.mainFragmentButton.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRus()
            // binding.mainFragmentButton.setImageResource(R.drawable.ic_russia)
        }
        isDataSetRus = !isDataSetRus
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.fragmentWetherListLoadingLayout.visibility = View.GONE
                binding.fragmentWetherListRecyclerView.isVisible = true
                adapter.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.fragmentWetherListLoadingLayout.visibility = View.VISIBLE
                binding.fragmentWetherListRecyclerView.isVisible = false
            }
            is AppState.Error -> {
                binding.fragmentWetherListLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.fragmentWetherListButton,
                        getString(R.string.error),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(getString(R.string.reload)) { viewModel.getWeatherFromLocalSourceRus() }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

interface OnItemViewClickListener {
    fun onItemViewClick(weather: Weather)
}