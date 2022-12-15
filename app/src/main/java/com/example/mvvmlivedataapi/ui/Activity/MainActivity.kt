package com.example.mvvmlivedataapi.ui.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmlivedataapi.R
import com.example.mvvmlivedataapi.databinding.ActivityMainBinding
import com.example.mvvmlivedataapi.databinding.ActivitySingleMovieBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        initView()
        setContentView(view)


    }

    private fun initView() {
        binding.goToNextPage.setOnClickListener {
            val intent = Intent(this, SingleMovieActivity::class.java)
            intent.putExtra("id", 736526)
            startActivity(intent)
        }
    }
}