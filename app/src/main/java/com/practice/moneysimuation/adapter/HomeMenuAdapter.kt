package com.practice.moneysimuation.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practice.moneysimuation.R
import com.practice.moneysimuation.activity.SimpleMoneySimulation

class HomeMenuAdapter(val context: Context, val menuList: MutableList<String>):
RecyclerView.Adapter<HomeMenuAdapter.HomeMenuViewHolder>(){

    class HomeMenuViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val tvMenu: TextView = itemView.findViewById(R.id.tvHomeMenuString)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_item_home_menu,parent,false)

        return HomeMenuViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: HomeMenuViewHolder,
        position: Int
    ) {
        val menu = menuList[position]

        holder.tvMenu.text = menu.toString()
        holder.itemView.setOnClickListener {

            val intent = Intent(context, SimpleMoneySimulation::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

}