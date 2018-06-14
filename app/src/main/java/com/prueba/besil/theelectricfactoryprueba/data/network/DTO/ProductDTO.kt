package com.prueba.besil.theelectricfactoryprueba.data.network.DTO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductDTO(@SerializedName("id") @Expose var id: Int,
                     @SerializedName("nombre") @Expose var name: String,
                     @SerializedName("img") @Expose var img: String,
                      @SerializedName("precio") @Expose var price: Double)