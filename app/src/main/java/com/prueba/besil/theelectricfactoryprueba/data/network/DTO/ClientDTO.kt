package com.prueba.besil.theelectricfactoryprueba.data.network.DTO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ClientDTO(@SerializedName("id") @Expose var id: Int,
                     @SerializedName("nombre") @Expose var name: String,
                     @SerializedName("direccion") @Expose var direction: String) : Serializable