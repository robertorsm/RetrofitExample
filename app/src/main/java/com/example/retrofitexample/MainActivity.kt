package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var weatherData: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherData = findViewById(R.id.textView)
        findViewById<View>(R.id.button).setOnClickListener { getCurrentData() }
    }

    private fun getCurrentData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WeatherService::class.java)
        val call = service.buscarTempoAtual(lat,lon, AppId)
        call.enqueue(object :Callback<WeatherResponse>{
            override fun onResponse(
                call: Call<WeatherResponse>?,
                response: Response<WeatherResponse>?
            ) {
                if (response !=null &&response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val stringBuilder = "País: " +
                            weatherResponse.sys!!.country +
                            "\n" +
                            "Temperatura: " +
                            weatherResponse.main!!.temp +
                            "\n" +
                            "Temperatura(Mínima): " +
                            weatherResponse.main!!.temp_min +
                            "\n" +
                            "Temperatura(Máxima): " +
                            weatherResponse.main!!.temp_max +
                            "\n" +
                            "Umidade: " +
                            weatherResponse.main!!.humidity +
                            "\n" +
                            "Pressão: " +
                            weatherResponse.main!!.pressure

                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                if (t != null) {
                    weatherData!!.text = t.message
                }
            }

        })
    }

    companion object{
        var BaseUrl = "http://api.openweathermap.org/"
        var AppId = "ca82196ddee52ecf9591eb57e752ca72"
        var lat = "35"
        var lon = "139"
    }
}