package com.example.wiproprojectassignment.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.wiproprojectassignment.R

/**
 * Activity for handling data and showing data
 */
@Suppress("DEPRECATION")
class WiproMainActivity : AppCompatActivity(), OnRefreshListener {

    private val model by lazy { ViewModelProvider(this).get(WiproCountryInfoListViewModel::class.java) }
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapterWipro: WiproCountryInfoListAdapter? = null
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout?.setOnRefreshListener(this)
        observeField()
        model.fetchData()
    }

    /**
     * Method to observe API response
     *
     */
    private fun observeField() {
        model.list.observe(this,
            Observer {
                Log.d("result", it.title)
                adapterWipro = WiproCountryInfoListAdapter(this, it.rows)
                val mLayoutManager: RecyclerView.LayoutManager =
                    LinearLayoutManager(this@WiproMainActivity, LinearLayoutManager.VERTICAL, false)
                recyclerView!!.layoutManager = mLayoutManager
                recyclerView!!.itemAnimator = DefaultItemAnimator()
                recyclerView!!.setHasFixedSize(true)
                recyclerView!!.adapter = adapterWipro
                swipeRefreshLayout!!.isRefreshing = false
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

    /**
     * Show dialog
     */
    private fun showLoading() {
        dialog = ProgressDialog(this).apply {
            setCanceledOnTouchOutside(false)
            setMessage("Please wait...")
        }
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    /**
     * Hide dialog
     */
    private fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    override fun onRefresh() {
        model.fetchData()
    }
}
