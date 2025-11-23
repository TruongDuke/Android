package com.example.playstorelist   // sửa đúng package

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rcvSection: RecyclerView
    private val sectionList = ArrayList<AppSection>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvSection = findViewById(R.id.rcvSection)

        initData()

        val adapter = SectionAdapter(sectionList)
        rcvSection.layoutManager = LinearLayoutManager(this)
        rcvSection.adapter = adapter
    }

    private fun initData() {
        // Section 1: Suggested for you
        val appsSuggested = listOf(
            AppItem(
                R.mipmap.ic_launcher_round,
                "Mech Assemble: Zombie Swarm",
                "Action • Role Playing • Roguelike",
                4.8,
                624
            ),
            AppItem(
                R.mipmap.ic_launcher_round,
                "MU: Hồng Hoả Đao",
                "Role Playing",
                4.8,
                339
            ),
            AppItem(
                R.mipmap.ic_launcher_round,
                "War Inc: Rising",
                "Strategy • Tower defense",
                4.9,
                231
            )
        )

        // Section 2: Recommended for you
        val appsRecommended = listOf(
            AppItem(R.mipmap.ic_launcher_round, "Suno - AI Music", "Music & Audio", 4.7, 120),
            AppItem(R.mipmap.ic_launcher_round, "Claude", "Productivity", 4.9, 80),
            AppItem(R.mipmap.ic_launcher_round, "DramaBox", "Entertainment", 4.5, 150)
        )

        sectionList.add(AppSection("Suggested for you", appsSuggested))
        sectionList.add(AppSection("Recommended for you", appsRecommended))
    }
}