package com.crowleysimon.current.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.crowleysimon.current.R
import com.crowleysimon.current.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> return@setOnItemSelectedListener true
            }
            false
        }
    }
}
