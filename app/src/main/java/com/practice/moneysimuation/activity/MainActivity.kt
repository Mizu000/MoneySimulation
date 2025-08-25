package com.practice.moneysimuation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.moneysimuation.R
import com.practice.moneysimuation.adapter.HomeMenuAdapter

class MainActivity : AppCompatActivity() {

    lateinit var rvHomeMenu: RecyclerView
    lateinit var menuList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        menuList = mutableListOf("Simple Money Simulation")

        //recycler
        rvHomeMenu = findViewById(R.id.rvHomeMenu)
        rvHomeMenu.layoutManager = LinearLayoutManager(this)
        rvHomeMenu.adapter = HomeMenuAdapter(this,menuList)

    }
}