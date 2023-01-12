package com.example.mycbandoeng

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private var dataList: ArrayList<CctvLinkItem>)
    : RecyclerView.Adapter<Adapter.ViewHolder>() {

    var onItemClick: ((CctvLinkItem) -> Unit)?= null

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cctvTitle : TextView = view.findViewById(R.id.title_cctv)
    }

    fun setFilteredList(dataList: ArrayList<CctvLinkItem>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.single_view, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.cctvTitle.text = data.title

        holder.cctvTitle.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    override fun getItemCount(): Int = dataList.size

}