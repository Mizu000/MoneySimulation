package com.practice.moneysimuation.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun showLongToast(context:Context,mess:String){
        Toast.makeText(context,mess,Toast.LENGTH_LONG).show()
    }

    fun showShortToast(context:Context,mess:String){
        Toast.makeText(context,mess,Toast.LENGTH_SHORT).show()
    }
}