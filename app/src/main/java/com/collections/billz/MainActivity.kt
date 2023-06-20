package com.collections.billz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.collections.billz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding = ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
    }
}