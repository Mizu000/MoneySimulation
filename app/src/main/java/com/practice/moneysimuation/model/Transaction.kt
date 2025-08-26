package com.practice.moneysimuation.model

data class Transaction(
    var note: String,
    var credit: Int,
    var debit: Int,
    var from: String,
    var to: String,
)
