package com.prueba.besil.theelectricfactoryprueba.ui.pedido.view

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.MVPView

interface PedidoMVPView : MVPView {
    fun swipeRefreshOff()
    fun loadProgress(enabled: Boolean)
    fun scrollToTop()
    fun itemClicked(pedido: Pedido, client: ClientDTO)
    fun updatePedidos(pedido: Pedido)
    fun showNoData(enabled: Boolean)
}