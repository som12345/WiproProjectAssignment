package com.example.wiproprojectassignment.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.wiproprojectassignment.R
import com.example.wiproprojectassignment.model.Row


class WiproCountryInfoListAdapter(private val context: FragmentActivity?) :
    RecyclerView.Adapter<WiproCountryInfoListAdapter.ViewHolder>() {
    private  var itemListRow: MutableList<Row> = mutableListOf()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    fun setData(itemList: MutableList<Row>) {
        this.itemListRow = itemList
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val itemList: Row = itemListRow[position]
        val title: String? = itemList.title ?: "No data found!"
        val desc: String? = itemList.description ?: "No data found!"
        val url: String? = itemList.imageHref
        holder.tvTitle.text = title
        holder.tvDesc.text = desc
        url?.let {
            setImageLogo(it, holder)
        } ?: run {
            holder.ivLogo.setImageResource(R.drawable.ic_no_img)
        }

    }

    private fun setImageLogo(
        url: String,
        holder: ViewHolder
    ) {
        context?.let {
            Glide.with(it)
                .load(url)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.ivLogo.setImageResource(R.drawable.ic_no_img)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.ivLogo.setImageDrawable(resource)
                        return false
                    }

                })
                .into(holder.ivLogo)
        }
    }

    override fun getItemCount(): Int {
        return itemListRow.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
        val ivLogo: ImageView = itemView.findViewById(R.id.ivLogo)

    }

}