package com.faris.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.faris.newsapp.BuildConfig
import com.faris.newsapp.R
import com.faris.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}