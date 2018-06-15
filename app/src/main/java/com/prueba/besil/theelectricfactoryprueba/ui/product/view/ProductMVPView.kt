package com.prueba.besil.theelectricfactoryprueba.ui.product.view

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.MVPView

interface ProductMVPView : MVPView {
    fun swipeRefreshOff()
    fun loadProgress(enabled: Boolean)
    fun updateProducts(pedido: Pedido)
    fun totalCalc()
}