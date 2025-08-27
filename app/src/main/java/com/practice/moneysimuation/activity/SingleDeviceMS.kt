package com.practice.moneysimuation.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.moneysimuation.R
import com.practice.moneysimuation.companionobject.IntentVariable
import com.practice.moneysimuation.model.Player

class SingleDeviceMS : AppCompatActivity() {

    lateinit var tvBalance: TextView
    lateinit var tvFrom: TextView
    lateinit var etAmount: EditText
    lateinit var btnSend: Button
    lateinit var spnPlayers: Spinner
    lateinit var player: Player
    lateinit var playerList: MutableList<Player>
    lateinit var playerNameList: MutableList<String>
    lateinit var playerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_device_ms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerList = mutableListOf()
        playerNameList = mutableListOf()

        tvBalance = findViewById(R.id.tvBalanceBanner)
        tvFrom = findViewById(R.id.tvFrom)
        etAmount = findViewById(R.id.etEnterAmount)
        btnSend = findViewById(R.id.btnSendMoney)
        spnPlayers = findViewById(R.id.spnToPlayer)

        val jsonPlayerList = intent.getStringExtra(IntentVariable.playerListIntent)
        val type = object : TypeToken<MutableList<Player>>(){}.type
        //
        val gson = Gson()
        val json = intent.getStringExtra(IntentVariable.playerIntent)
        player = gson.fromJson(json,Player::class.java)
        playerList = gson.fromJson(jsonPlayerList,type)

        for(playerTemp in playerList){
            if(playerTemp != player){
                playerNameList.add(player.name)
            }

        }

        //
        tvBalance.text = "$${player.balance}"
        tvFrom.text = player.name.toString()

        //spinner player
        playerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, playerNameList)
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPlayers.adapter = playerAdapter


    }
}