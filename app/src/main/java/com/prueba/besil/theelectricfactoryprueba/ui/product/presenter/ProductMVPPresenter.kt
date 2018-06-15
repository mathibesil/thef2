package com.prueba.besil.theelectricfactoryprueba.ui.product.presenter

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.MVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductMVPView

interface ProductMVPPresenter<V:ProductMVPView, I:ProductMVPInteractor> : MVPPresenter<V,I>{
    fun getPedido(idPedido: Int, client: ClientDTO)
    abstract fun savePedido(pedido: Pedido)
}