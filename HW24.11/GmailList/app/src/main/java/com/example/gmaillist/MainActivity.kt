package com.example.gmaillist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var rcvEmail: RecyclerView
    private lateinit var fabCompose: FloatingActionButton
    private lateinit var adapter: EmailAdapter
    private val emailList = ArrayList<Email>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvEmail = findViewById(R.id.rcvEmail)
        fabCompose = findViewById(R.id.fabCompose)

        initData()

        adapter = EmailAdapter(emailList)
        rcvEmail.layoutManager = LinearLayoutManager(this)
        rcvEmail.adapter = adapter

        fabCompose.setOnClickListener {
            Toast.makeText(this, "Compose new email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData() {
        emailList.add(
            Email(
                sender = "Edurila.com",
                subject = "$19 Only (First 10 spots) - Bestselling...",
                snippet = "Are you looking to Learn Web Designing...",
                time = "12:34 PM",
                starred = true
            )
        )
        emailList.add(
            Email(
                "Chris Abad",
                "Help make Campaign Monitor better",
                "Let us know your thoughts! No Images...",
                "11:22 AM"
            )
        )
        emailList.add(
            Email(
                "Tuto.com",
                "8h de formation gratuite et les nouvea...",
                "Photoshop, SEO, Blender, CSS, WordPress...",
                "11:04 AM"
            )
        )
        emailList.add(
            Email(
                "Support",
                "Suivi de vos services",
                "Société OVH : suivi de vos services ...",
                "10:26 AM"
            )
        )
        emailList.add(
            Email(
                "Matt from Ionic",
                "The New Ionic Creator Is Here!",
                "Announcing the all-new Creator, build...",
                "9:15 AM"
            )
        )
    }
}