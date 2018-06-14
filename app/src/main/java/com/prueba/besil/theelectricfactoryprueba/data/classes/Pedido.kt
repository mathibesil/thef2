package com.prueba.besil.theelectricfactoryprueba.data.classes

import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import java.util.*

class Pedido(val idPedido: Int, val client: ClientDTO, var date: Date, var pedidoProductList: List<PedidoProduct>){
    class PedidoProduct(val product: ProductDTO, val quantity: Int)
}