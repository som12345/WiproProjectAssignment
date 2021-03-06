package com.example.wiproprojectassignment.ui

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.wiproprojectassignment.R
import com.example.wiproprojectassignment.utils.Utils


class WiproDataShowFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val model by lazy { ViewModelProvider(this).get(WiproCountryInfoListViewModel::class.java) }
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapterWipro: WiproCountryInfoListAdapter? = null
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_wipro_data_show, container, false)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout?.setOnRefreshListener(this)
        adapterWipro = WiproCountryInfoListAdapter(activity)
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = mLayoutManager
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapterWipro
        observeField()
        callApi()
        return view
    }

    private fun callApi() {
        if (activity?.let { Utils.checkInternet(it) } == true) {
            model.fetchData()
        } else {
            showAlert()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(activity)
        with(builder)
        {
            setTitle(getString(R.string.please_check_internet))
            setMessage(getString(R.string.no_internet))
            setPositiveButton(
                getString(R.string.ok_text),
                DialogInterface.OnClickListener(function = positiveButtonClick as (DialogInterface, Int) -> Unit)
            )
            setNegativeButton(
                getString(R.string.retry_text),
                DialogInterface.OnClickListener(function = negativeButton as (DialogInterface, Int) -> Unit)
            )
            show()
        }
    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
       activity?.finish()
    }
    private val negativeButton = { dialog: DialogInterface, which: Int ->
        callApi()
    }
    companion object {
        @JvmStatic
        fun newInstance() = WiproDataShowFragment()
    }

    /**
     * Method to observe API response
     *
     */
    private fun observeField() {
        activity?.let {
            model.list.observe(
                it,
                Observer { it ->
                    Log.d("result", it.title)
                    val updatedData = it.rows.toMutableList()
                    val filterData = updatedData.filterNot { it1 ->
                        it1.title==null && it1.description==null && it1.imageHref==null
                    }
                    adapterWipro?.setData(filterData.toMutableList())
                    adapterWipro?.notifyDataSetChanged()
                    swipeRefreshLayout?.isRefreshing = false
                })
        }

        activity?.let {
            model.toastMsg.observe(
                it,
                Observer {
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                })
        }

        activity?.let {
            model.showLoader.observe(it,
                Observer {
                    if (it) showLoading() else hideLoading()
                }
            )
        }
    }

    /**
     * Show dialog
     */
    private fun showLoading() {
        activity?.let {
            dialog = ProgressDialog(it).apply {
                setCanceledOnTouchOutside(false)
                setMessage("Please wait...")
            }
            if (!dialog.isShowing) {
                dialog.show()
            }
        }
    }

    /**
     * Hide dialog
     */
    private fun hideLoading() {
        if (dialog.isShowing) dialog.dismiss()
    }

    override fun onRefresh() {
        callApi()
    }
}