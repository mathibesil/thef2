package com.prueba.besil.theelectricfactoryprueba.data.network.util

import android.content.Context
import android.net.ConnectivityManager

open class NetworkUtil {
    companion object {
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
    }
}