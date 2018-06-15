package com.prueba.besil.theelectricfactoryprueba.data.network

import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.util.AppConstant.BASEURL
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ThefService {
    @GET(BASEURL + "clients.json")
    fun getClients(): Observable<List<ClientDTO>>
    @GET(BASEURL + "products.json")
    fun getProducts(): Observable<List<ProductDTO>>
    @GET(BASEURL + "clientById.php?")
    fun getClientById(@Query("id") id: Int): Observable<ClientDTO>
}