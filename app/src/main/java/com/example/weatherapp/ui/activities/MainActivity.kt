package com.example.weatherapp.ui.activities

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R
import com.example.weatherapp.data.models.CurrentResponseApi
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.viewmodels.WeatherViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            var lat = 51.50
            var lon = -0.12
            var name = "London"

            tvCity.text = name
            progressBar.visibility = View.VISIBLE
            weatherViewModel.loadCurrentWeather(lat, lon, "metric")
                .enqueue(object : Callback<CurrentResponseApi> {
                    override fun onResponse(
                        call: Call<CurrentResponseApi>,
                        response: Response<CurrentResponseApi>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            progressBar.visibility = View.GONE
                            layoutDetail.visibility = View.VISIBLE
                            data?.let {
                                tvStatus.text = it.weather?.get(0)?.main ?: "-"
                                tvWind.text = it.wind?.speed?.let { Math.round(it).toString() } + "Km"
                                tvHumidity.text = it.main?.humidity?.toString() + "%"
                                tvCurrentTemp.text =
                                    it.main?.temp?.let { Math.round(it).toString() } + "°"
                                tvMinTemp.text =
                                    it.main?.tempMin?.let { Math.round(it).toString() } + "°"
                                tvMaxTemp.text =
                                    it.main?.tempMax?.let { Math.round(it).toString() } + "°"

                                val drawable = if (isNightNow()) R.drawable.night_bg
                                else {
                                    setDynamicallyWallpaper(it.weather?.get(0)?.icon?:"-")

                                }
                                bgWeather.setImageResource(drawable)

                            }
                        }
                    }

                    override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun setDynamicallyWallpaper(icon: String): Int {
        return when (icon.dropLast(1)) {
            "01" -> {
                R.drawable.snow_bg
            }

            "02", "03", "04" -> {
                R.drawable.cloudy_bg
            }

            "09", "10", "11" -> {
                R.drawable.rainy_bg
            }

            "13" -> {
                R.drawable.snow_bg
            }

            "50" -> {
                R.drawable.haze_bg
            }

            else -> 0
        }
    }
}