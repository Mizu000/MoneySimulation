package com.practice.moneysimuation.activity

import android.content.Intent
import android.os.Bundle
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
import com.practice.moneysimuation.R
import com.practice.moneysimuation.model.Player
import com.practice.moneysimuation.utils.ToastUtil

class SimpleMoneySimulation : AppCompatActivity() {

    lateinit var imgBtnAddPlayer: ImageButton
    lateinit var btnStart: Button
    lateinit var spnPlayers: Spinner
    lateinit var playerList: MutableList<Player>
    lateinit var playerAdapter: ArrayAdapter<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simple_money_simulation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playerList = mutableListOf(

        )

        imgBtnAddPlayer  =  findViewById(R.id.btnAddPlayers)
        btnStart         =  findViewById(R.id.btnSimpleMs)
        spnPlayers       =  findViewById(R.id.spinnerPlayers)

        //spinner category
        playerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, playerList)
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPlayers.adapter = playerAdapter

//        categoryList.addAll(list)
//        categoryAdapter.notifyDataSetChanged()

        imgBtnAddPlayer.setOnClickListener {

            openAddPlayerDialog()
        }

        btnStart.setOnClickListener {

            startMSBoard()

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
                playerAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                ToastUtil.showShortToast(this,"Please Enter All field")
            }

        }

    }

    fun startMSBoard(){
        val intent = Intent(this, MSBoard::class.java)
        startActivity(intent)
    }


}