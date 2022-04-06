package com.tengenezalabs.wasanii.ui.main

import android.content.ClipData.newIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tengenezalabs.wasanii.R
import com.tengenezalabs.wasanii.databinding.ActivitySplashBinding
import com.tengenezalabs.wasanii.utils.isNetworkAvailable
import com.tengenezalabs.wasanii.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel = SplashViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        //check network connectivity
        if(!isNetworkAvailable(this)){
            //show error
            binding.root.snackbar(getString(R.string.no_internet_connection))
            binding.progressBar.visibility = View.GONE

            GlobalScope.launch {
                delay(15000)
                if(!isNetworkAvailable(this@SplashActivity)){
                    binding.root.snackbar(getString(R.string.no_internet_connection))
                }else{
                    loadMainActivity()
                }
            }

        } else {
            loadMainActivity()
        }
    }

    private fun loadMainActivity(){
        binding.progressBar.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}