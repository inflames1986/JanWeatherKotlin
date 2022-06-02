package com.inflames1986.janweatherkotlin.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inflames1986.janweatherkotlin.R
import com.inflames1986.janweatherkotlin.model.entities.Weather

class WeatherListFragmentAdapter(private var onItemViewClickListener: OnItemListClickListener?) :
    RecyclerView.Adapter<WeatherListFragmentAdapter.MainViewHolder>() {

    private var weatherList: List<Weather> = listOf()

    fun setWeather(data: List<Weather>) {
        weatherList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_weather_list_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weather: Weather) {
            with(itemView) {
                findViewById<TextView>(R.id.FragmentWetherListRecyclerItemTextView).text =
                    weather.city.name
                setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        weather.city.name,
                        Toast.LENGTH_LONG
                    ).show()
                }
                setOnClickListener {
                    onItemViewClickListener?.onItemListClick(weather)
                }
            }
        }
    }

    fun removeListener() {
        onItemViewClickListener = null
    }
}
