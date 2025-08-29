package com.practice.moneysimuation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practice.moneysimuation.R
import com.practice.moneysimuation.adapter.PlayersAdapter.PlayerViewHolder
import com.practice.moneysimuation.model.Transaction

class TranAdapter(val context: Context, val tranList: MutableList<Transaction>):
RecyclerView.Adapter<TranAdapter.TranViewHolder>()
{

    class TranViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val tvNote: TextView = itemView.findViewById(R.id.tvTranNote)
        //        val tvBalance: TextView = itemView.findViewById(R.id.tvPlayerBalance)
        val tvCredit: TextView = itemView.findViewById(R.id.tvTranCredit)
        val tvDebit: TextView = itemView.findViewById(R.id.tvTranDebit)
        val tvFrom: TextView = itemView.findViewById(R.id.tvTranFrom)
        val tvTo: TextView = itemView.findViewById(R.id.tvTranTo)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TranViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tran_layout,parent,false)

        return TranViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TranViewHolder,
        position: Int
    ) {
        val tran = tranList[position]
        holder.tvNote.text = tran.note
        holder.tvCredit.text = "Credit: ${tran.credit}"
        holder.tvDebit.text =  "Debit: ${tran.debit}"
        holder.tvFrom.text = "From: ${tran.from}"
        holder.tvTo.text = "To: ${tran.to}"
    }

    override fun getItemCount(): Int {
        return tranList.size
    }



}