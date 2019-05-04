package com.nikitamaslov.core.network

import android.content.Context
import android.net.ConnectivityManager

interface NetworkManager {

    val isConnected: Boolean
}

class NetworkManagerImpl(private val applicationContext: Context) : NetworkManager {

    override val isConnected: Boolean get() = isConnected(applicationContext)

    @JvmName("isConnectedFunction")
    private fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
