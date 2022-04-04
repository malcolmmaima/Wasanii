package com.tengenezalabs.wasanii.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tengenezalabs.wasanii.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}