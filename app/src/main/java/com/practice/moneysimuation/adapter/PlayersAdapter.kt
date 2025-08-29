package com.practice.moneysimuation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.practice.moneysimuation.R
import com.practice.moneysimuation.activity.MSBoard
import com.practice.moneysimuation.activity.SingleDeviceMS
import com.practice.moneysimuation.companionobject.IntentVariable
import com.practice.moneysimuation.model.Player
import com.practice.moneysimuation.model.Transaction
import com.practice.moneysimuation.utils.ToastUtil

class PlayersAdapter(val context: Context, val playerList: MutableList<Player>,val transactionList: MutableList<Transaction>):
    RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>(){


    class PlayerViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val tvName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val tvBalance: TextView = itemView.findViewById(R.id.tvPlayerBalance)
        val tvView: TextView = itemView.findViewById(R.id.tvViewBalance)
        val btnLogin: Button = itemView.findViewById(R.id.btnLogin)
        val etEnterPin: TextView = itemView.findViewById(R.id.etEnterPin)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.player_layout,parent,false)

        return PlayerViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: PlayerViewHolder,
        position: Int
    ) {
        val player = playerList[position]
        holder.tvName.text = player.name
        holder.tvBalance.text = "*****"
        holder.tvView.text = "View Balance"
        //
        holder.btnLogin.setOnClickListener {

            val pin = holder.etEnterPin.text.trim()
            if(pin.isNotEmpty()){
                if(pin.toString().toInt() == player.upiPin){



                    val gson = Gson()
                    val json = gson.toJson(player)
                    val jsonPlayerList = gson.toJson(playerList)
                    val jsonTranList = gson.toJson(transactionList)
                    val intent = Intent(context, SingleDeviceMS::class.java)
                    intent.putExtra(IntentVariable.playerIntent,json)
                    intent.putExtra(IntentVariable.playerListIntent,jsonPlayerList)
                    intent.putExtra(IntentVariable.transactionListIntent,jsonTranList)
                    context.startActivity(intent)

                }else{
                    ToastUtil.showShortToast(context,"Wrong Pin")
                }
            }else{
                ToastUtil.showShortToast(context,"Enter Pin")
            }


        }
        //0
        holder.tvView.setOnClickListener {

            val balance = holder.tvBalance.text.toString()
            if(balance == "*****"){

                showBalance(holder,player)
            }else{
                holder.tvBalance.text = "*****"
                holder.tvView.text = "View Balance"
            }

        }
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    fun showBalance(holder:PlayerViewHolder,player:Player){
        val pin = holder.etEnterPin.text.trim()
        if(pin.isNotEmpty()){
            if(pin.toString().toInt() == player.upiPin){
                holder.tvView.text = "Hide Balance"
                holder.etEnterPin.text = ""
                holder.tvBalance.text = player.balance.toString()
            }else{
                ToastUtil.showShortToast(context,"Wrong Pin")
            }
        }else{
            ToastUtil.showShortToast(context,"Enter Pin")
        }
    }


}