package com.practice.moneysimuation.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
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
import com.practice.moneysimuation.utils.ToastUtil

class MSBoard : AppCompatActivity() {

    lateinit var playerList: MutableList<Player>
    lateinit var transactionList: MutableList<Transaction>
    lateinit var rvPlayers: RecyclerView
    lateinit var btnEndGame: Button
    var continueMs = false
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_msboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferences = getSharedPreferences(SharedPrefVariable.prefFile,MODE_PRIVATE)

        rvPlayers = findViewById(R.id.rvPlayers)
        btnEndGame = findViewById(R.id.btnEndGame)

        playerList = mutableListOf()
        transactionList = mutableListOf()


        continueMs = intent.getBooleanExtra(IntentVariable.continueMS,false)



        if(continueMs){
            getContinue()
        }else{
            val gson = Gson()
            val json = intent.getStringExtra(IntentVariable.playerListIntent)

//        val jsonTran = intent.getStringExtra(IntentVariable.playerListIntent)
            val typePlayer = object : TypeToken<MutableList<Player>>(){}.type
//        val typeTrans = object : TypeToken<MutableList<Transaction>>(){}.type
            playerList = gson.fromJson(json,typePlayer)
//        transactionList = gson.fromJson(jsonTran,typeTrans)

            // Save playerList in SharedPreferences
            preferences.edit {
                putString(SharedPrefVariable.playerList, json)
            }

        }


        rvPlayers.layoutManager = LinearLayoutManager(this)
        rvPlayers.adapter = PlayersAdapter(this,playerList)

        btnEndGame.setOnClickListener {
            playerList.clear()
            transactionList.clear()

            val gson = Gson()
            val jsonPl = gson.toJson(playerList)
            val jsontr = gson.toJson(transactionList)
            // Save playerList in SharedPreferences
            preferences.edit {
                putString(SharedPrefVariable.playerList, jsonPl)
                putString(SharedPrefVariable.transaction, jsontr)
            }
            finishAffinity()
        }

    }

    fun getContinue() {

        playerList.clear()
        transactionList.clear()

        val gson = Gson()
        val jsonPlayerList = preferences.getString(SharedPrefVariable.playerList, null)
        val jsonTransactionList = preferences.getString(SharedPrefVariable.transaction, null)

        if (!jsonPlayerList.isNullOrEmpty()) {
            val typePlayer = object : TypeToken<MutableList<Player>>() {}.type
            val typeTran = object : TypeToken<MutableList<Transaction>>() {}.type
            playerList = gson.fromJson(jsonPlayerList, typePlayer)
            transactionList = gson.fromJson(jsonTransactionList, typeTran)



        }
    }

}