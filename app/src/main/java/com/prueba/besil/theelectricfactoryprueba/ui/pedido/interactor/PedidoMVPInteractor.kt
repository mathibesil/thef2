package com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.MVPInteractor
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import io.reactivex.Observable

interface PedidoMVPInteractor : MVPInteractor {
    fun getPedidos(): List<HashMap<String, Any>>
    fun getClient(idClient: Int): Observable<ClientDTO>
}