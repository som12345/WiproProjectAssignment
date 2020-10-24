package com.example.wiproprojectassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wiproprojectassignment.R

/**
 * Activity for handling data and showing data
 */
@Suppress("DEPRECATION")
class WiproMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var  currentFragment = WiproDataShowFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, currentFragment, "WIPRO_FRAGMENT")
            .commit();
    }

}
