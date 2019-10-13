package com.crowleysimon.current.ui

import android.os.Bundle
import com.crowleysimon.current.R
import com.crowleysimon.current.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
