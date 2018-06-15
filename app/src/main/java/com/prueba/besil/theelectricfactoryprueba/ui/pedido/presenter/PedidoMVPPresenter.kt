package com.prueba.besil.theelectricfactoryprueba.ui.pedido.presenter

import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.MVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor.PedidoMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoMVPView


interface PedidoMVPPresenter<V: PedidoMVPView,I:PedidoMVPInteractor> : MVPPresenter<V,I>{
    fun getPedidos()
}