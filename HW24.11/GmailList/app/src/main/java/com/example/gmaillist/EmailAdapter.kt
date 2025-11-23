package com.example.gmaillist   // sửa đúng package

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmailAdapter(
    private val emails: List<Email>
) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAvatar: TextView = itemView.findViewById(R.id.txtAvatar)
        val txtSender: TextView = itemView.findViewById(R.id.txtSender)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val txtSubject: TextView = itemView.findViewById(R.id.txtSubject)
        val txtSnippet: TextView = itemView.findViewById(R.id.txtSnippet)
        val imgStar: ImageView = itemView.findViewById(R.id.imgStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_email, parent, false)
        return EmailViewHolder(view)
    }

    override fun getItemCount() = emails.size

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emails[position]

        holder.txtSender.text = email.sender
        holder.txtSubject.text = email.subject
        holder.txtSnippet.text = email.snippet
        holder.txtTime.text = email.time

        // Chữ cái đầu cho avatar
        holder.txtAvatar.text = email.sender.first().uppercase()

        // Hiển thị sao
        holder.imgStar.setImageResource(
            if (email.starred)
                android.R.drawable.btn_star_big_on
            else
                android.R.drawable.btn_star_big_off
        )
    }
}