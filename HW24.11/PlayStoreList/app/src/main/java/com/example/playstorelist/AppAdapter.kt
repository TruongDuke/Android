package com.example.playstorelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    private val apps: List<AppItem>
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        val txtAppName: TextView = itemView.findViewById(R.id.txtAppName)
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
        val txtInfo: TextView = itemView.findViewById(R.id.txtInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun getItemCount() = apps.size

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]
        holder.imgIcon.setImageResource(app.iconResId)
        holder.txtAppName.text = app.name
        holder.txtCategory.text = app.category
        holder.txtInfo.text = "${app.rating} ★  •  ${app.sizeMb} MB"
    }
}