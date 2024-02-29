package com.example.randomcatgenerator

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.example.randomcatgenerator.api.BASE_URL
import com.example.randomcatgenerator.api.Cat
import com.example.randomcatgenerator.api.CatService
import com.example.randomdoggenerator.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Keeps the phone in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Starts up the background transitions
        backgroundAnimation()

        // Makes an API request as soon as the app starts
        makeApiRequest()

        binding.floatingActionButton.setOnClickListener {

            // FAB rotate animation
            binding.floatingActionButton.animate().apply {
                rotationBy(360f)
                duration = 1000
            }.start()

            makeApiRequest()
            binding.ivRandomObj.visibility = View.GONE

        }
    }

    private fun backgroundAnimation() {
        val animatonDrawable: AnimationDrawable = binding.rlLayout.background as AnimationDrawable
        animatonDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }

    private fun makeApiRequest() {
        val retrofitCat = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val catService: CatService = retrofitCat.create(CatService::class.java)
        val callCat = catService.randomCat().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                val randomCat = response.body()!!

                Log.i("catMessage", randomCat[0].url)

                Picasso.get()
                    .load(randomCat[0].url) // get the first result as the API states
                    .into(binding.ivRandomObj)
                    binding.ivRandomObj.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                Log.i("catError", t.toString())
            }
        })
    }
}