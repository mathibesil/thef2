package com.prueba.besil.theelectricfactoryprueba.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("access_token") @Expose var token: String)