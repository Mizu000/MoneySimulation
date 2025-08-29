package com.practice.moneysimuation.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
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
import com.practice.moneysimuation.adapter.TranAdapter
import com.practice.moneysimuation.companionobject.IntentVariable
import com.practice.moneysimuation.companionobject.SharedPrefVariable
import com.practice.moneysimuation.model.Player
import com.practice.moneysimuation.model.Transaction
import com.practice.moneysimuation.utils.ToastUtil

class SingleDeviceMS : AppCompatActivity() {

    lateinit var tvBalance: TextView
    lateinit var tvFrom: TextView
    lateinit var etAmount: EditText
    lateinit var rvTran: RecyclerView
    lateinit var TranAdapter : TranAdapter
    lateinit var btnSend: Button
    lateinit var spnPlayers: Spinner
    lateinit var playerName: String
    lateinit var player: Player
    lateinit var playerList: MutableList<Player>
    lateinit var transactionList: MutableList<Transaction>
    lateinit var playerNameList: MutableList<String>
    lateinit var playerAdapter: ArrayAdapter<String>
    lateinit var preferences: SharedPreferences
    lateinit var to: String
    lateinit var from: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_device_ms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferences = getSharedPreferences(SharedPrefVariable.prefFile,MODE_PRIVATE)

        transactionList = mutableListOf()
        playerList = mutableListOf()
        playerNameList = mutableListOf()

        tvBalance = findViewById(R.id.tvBalanceBanner)
        tvFrom = findViewById(R.id.tvFrom)
        etAmount = findViewById(R.id.etEnterAmount)
        btnSend = findViewById(R.id.btnSendMoney)
        spnPlayers = findViewById(R.id.spnToPlayer)
        rvTran = findViewById(R.id.rvTran)


        //
        val gson = Gson()
        playerName = intent.getStringExtra(IntentVariable.playerIntent).toString()
//        player = gson.fromJson(json,Player::class.java)

        val typePlayer = object : TypeToken<MutableList<Player>>() {}.type
        val typeTran = object : TypeToken<MutableList<Transaction>>() {}.type

// Get JSON strings from SharedPreferences
        val jsonPlayerList = preferences.getString(SharedPrefVariable.playerList, null)
        val jsonTranList = preferences.getString(SharedPrefVariable.transaction, null)

// Deserialize safely (fallback to empty list if null)
        playerList = gson.fromJson<MutableList<Player>>(jsonPlayerList, typePlayer) ?: mutableListOf()
        transactionList = gson.fromJson<MutableList<Transaction>>(jsonTranList, typeTran) ?: mutableListOf()


        for(playerTemp in playerList){
            if(playerTemp.name != playerName){
                playerNameList.add(playerTemp.name)
            }else{
                player = playerTemp
            }

        }

        //
        from = playerName
        tvBalance.text = "$${player.balance}"
        tvFrom.text = from

        rvTran.layoutManager = LinearLayoutManager(this)
        TranAdapter = TranAdapter(this,transactionList)
        rvTran.adapter = TranAdapter

        savePlayerState()

        //spinner player
        playerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, playerNameList)
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnPlayers.adapter = playerAdapter

        spnPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                to = playerNameList[position]   // ✅ get the selected name
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                to = playerNameList[0]
            }
        }

        ///
        btnSend.setOnClickListener {
            val amount = etAmount.text.toString().trim()
            if (amount.isNotEmpty()) {
                //credit
                transaction(amount.toInt(), 0, from, to)

                //debit
                transaction(0, amount.toInt(), from, to)
            } else {
                ToastUtil.showShortToast(this,"Enter Amount")
            }
        }



    }

    fun transaction(
        credit: Int,
        debit: Int,
        from: String,
        to: String
    ) {
        // Update balances
        val sender = playerList.find { it.name == from }
        val receiver = playerList.find { it.name == to }

        sender?.balance = (sender?.balance ?: 0) - debit
        receiver?.balance = (receiver?.balance ?: 0) + credit

        // Update UI (use sender’s balance if current player is sender)
        if (player.name == from) {
            tvBalance.text = "$${sender?.balance}"
        } else if (player.name == to) {
            tvBalance.text = "$${receiver?.balance}"
        }

        // Save transaction (one record is enough)
        val transaction = Transaction("", credit, debit, from, to)
        transactionList.add(transaction)

        val gson = Gson()
        val json = gson.toJson(transactionList)

        preferences.edit {
            putString(SharedPrefVariable.transaction, json)
        }

        savePlayerState()
        TranAdapter.notifyDataSetChanged()
    }


    fun savePlayerState(){
        val gson = Gson()
        val json = gson.toJson(playerList)

        preferences.edit {
            putString(SharedPrefVariable.playerList, json)
        }   // or editor.commit()
    }

}