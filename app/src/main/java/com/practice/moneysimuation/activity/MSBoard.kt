package com.practice.moneysimuation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.moneysimuation.R
import com.practice.moneysimuation.adapter.PlayersAdapter
import com.practice.moneysimuation.companionobject.IntentVariable
import com.practice.moneysimuation.companionobject.SharedPrefVariable
import com.practice.moneysimuation.model.Player
import com.practice.moneysimuation.model.Transaction

class MSBoard : AppCompatActivity() {

    lateinit var playerList: MutableList<Player>
    lateinit var transactionList: MutableList<Transaction>
    lateinit var rvPlayers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_msboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvPlayers = findViewById(R.id.rvPlayers)


        val gson = Gson()
        val json = intent.getStringExtra(IntentVariable.playerListIntent)
        val jsonTran = intent.getStringExtra(IntentVariable.playerListIntent)
        val typePlayer = object : TypeToken<MutableList<Player>>(){}.type
        val typeTrans = object : TypeToken<MutableList<Transaction>>(){}.type
        playerList = gson.fromJson(json,typePlayer)
        transactionList = gson.fromJson(jsonTran,typeTrans)

        rvPlayers.layoutManager = LinearLayoutManager(this)
        rvPlayers.adapter = PlayersAdapter(this,playerList,transactionList)

    }
}