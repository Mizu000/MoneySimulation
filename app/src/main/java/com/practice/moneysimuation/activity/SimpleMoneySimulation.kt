package com.practice.moneysimuation.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.practice.moneysimuation.R
import com.practice.moneysimuation.companionobject.IntentVariable
import com.practice.moneysimuation.companionobject.SharedPrefVariable
import com.practice.moneysimuation.model.Player
import com.practice.moneysimuation.utils.ToastUtil
import androidx.core.content.edit
import com.google.gson.reflect.TypeToken
import com.practice.moneysimuation.model.Transaction

class SimpleMoneySimulation : AppCompatActivity() {

    lateinit var imgBtnAddPlayer: ImageButton
    lateinit var btnStart: Button
    lateinit var btnContinue: Button
    lateinit var spnPlayers: Spinner
    lateinit var preferences: SharedPreferences
    lateinit var playerList: MutableList<Player>
    lateinit var transactionList: MutableList<Transaction>
    lateinit var playerNameList: MutableList<String>
    lateinit var playerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simple_money_simulation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferences = getSharedPreferences(SharedPrefVariable.prefFile,MODE_PRIVATE)

        playerList = mutableListOf()
        transactionList = mutableListOf()
        playerNameList = mutableListOf()

        imgBtnAddPlayer  =  findViewById(R.id.btnAddPlayers)
        btnStart         =  findViewById(R.id.btnSimpleMs)
        btnContinue         =  findViewById(R.id.btnContinueSingleDMS)
        spnPlayers       =  findViewById(R.id.spinnerPlayers)

        //spinner player
        playerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, playerNameList)
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPlayers.adapter = playerAdapter

        getContinue()

//        categoryList.addAll(list)
//        categoryAdapter.notifyDataSetChanged()

        imgBtnAddPlayer.setOnClickListener {

            openAddPlayerDialog()
        }

        btnContinue.setOnClickListener {

            startMSBoard()
        }

        btnStart.setOnClickListener {

            if(playerList.isNotEmpty() && playerList.size >2){
                startMSBoard()
            }else{
                ToastUtil.showShortToast(this,"Please Add Player")
            }


        }

    }

    //
    fun getContinue() {
        val gson = Gson()
        val jsonPlayerList = preferences.getString(SharedPrefVariable.playerList, null)
        val jsonTransactionList = preferences.getString(SharedPrefVariable.transaction, null)

        if (!jsonPlayerList.isNullOrEmpty()) {
            val typePlayer = object : TypeToken<MutableList<Player>>() {}.type
            val typeTran = object : TypeToken<MutableList<Transaction>>() {}.type
            playerList = gson.fromJson(jsonPlayerList, typePlayer)
            transactionList = gson.fromJson(jsonTransactionList, typeTran)
        } else {
            btnContinue.visibility = View.GONE
            // nothing saved yet, maybe start fresh
            playerList = mutableListOf()
            transactionList = mutableListOf()
        }
    }



    fun openAddPlayerDialog(){
        val dialogView = layoutInflater.inflate(R.layout.input_player_info, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()

// Access views inside dialog
        val etPlayerName = dialogView.findViewById<EditText>(R.id.etPlayerName)
        val etUpiPin = dialogView.findViewById<EditText>(R.id.etPlayerUpiPin)
        val etPlayerBalance = dialogView.findViewById<EditText>(R.id.etPlayerBalance)
        val btnAdd = dialogView.findViewById<Button>(R.id.btnAddPlayerInfo)

        btnAdd.setOnClickListener {
            val name = etPlayerName.text.toString()
            val balance = etPlayerBalance.text.toString()
            val pin = etUpiPin.text.toString()
            if(name.isNotEmpty() && pin.isNotEmpty() && balance.isNotEmpty()){
                playerList.add(Player(name,balance.toInt(),pin.toInt()))
                playerNameList.add(name)
                playerAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                ToastUtil.showShortToast(this,"Please Enter All field")
            }

        }

    }

    fun startMSBoard(){

        val gson = Gson()
        val jsonPlayer = gson.toJson(playerList)
        val jsonTrans = gson.toJson(transactionList)

//        preferences.edit {
//            putString(SharedPrefVariable.playerList, json)
//        }   // or editor.commit()


        val intent = Intent(this, MSBoard::class.java)
        intent.putExtra(IntentVariable.playerListIntent,jsonPlayer)
        intent.putExtra(IntentVariable.transactionListIntent,jsonTrans)
        startActivity(intent)
    }


}