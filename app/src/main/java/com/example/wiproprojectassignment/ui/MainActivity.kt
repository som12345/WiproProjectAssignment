package com.example.wiproprojectassignment.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wiproprojectassignment.R

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val model by lazy { ViewModelProvider(this).get(CountryInfoListViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeField()
        model.fetchData()
    }

    private fun observeField() {
        model.list.observe(this,
            Observer {
                Log.d("result", it.title)
            })

        model.toastMsg.observe(this,
            Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        model.showLoader.observe(this,
            Observer {
                if (it) showLoading() else hideLoading()
            }
        )
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }
}
