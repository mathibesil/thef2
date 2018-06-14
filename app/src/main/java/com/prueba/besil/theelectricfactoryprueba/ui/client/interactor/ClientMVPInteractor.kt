package com.prueba.besil.theelectricfactoryprueba.ui.client.interactor

import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.MVPInteractor
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import io.reactivex.Observable

interface ClientMVPInteractor : MVPInteractor {
    fun getClients(): Observable<List<ClientDTO>>

}