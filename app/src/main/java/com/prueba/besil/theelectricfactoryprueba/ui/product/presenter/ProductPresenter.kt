package com.prueba.besil.theelectricfactoryprueba.ui.product.presenter

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.util.NoConnectivityException
import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.BasePresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductMVPView
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class ProductPresenter<V : ProductMVPView, I : ProductMVPInteractor> @Inject internal constructor(interactor: I) : BasePresenter<V, I>(interactor), ProductMVPPresenter<V, I> {
    fun getProducts(client: ClientDTO, pedidosProducts: List<HashMap<String, Any>>?, idPedido: Int) {
        getView()?.loadProgress(true)
        interactor?.getProducts()
                ?.subscribe({
                    //Obtengo productos desde internet.
                    val listProducts: MutableList<Pedido.PedidoProduct> = mutableListOf()
                    for (product: ProductDTO in it) {
                        var quantityTemp =0
                        //si es un pedido a modificar cargo cantidades en los productos.
                        if (pedidosProducts != null) {
                            for(hash:HashMap<String, Any> in pedidosProducts){
                                if(hash["idProduct"]==product.id){
                                    quantityTemp = hash["quantity"].toString().toInt()
                                }
                            }
                        }
                        //cargo productos en lista
                        listProducts.add(Pedido.PedidoProduct(product, quantityTemp))
                    }
                    //le devuelvo a la view un pedido (el nuevo o uno a modificar), con sus respectivos
                    //productos para mostrar.
                    getView()?.updateProducts(Pedido(idPedido, client, Calendar.getInstance().time , listProducts))
                    getView()?.loadProgress(false)
                }, { error: Throwable ->
                    //muestro errores
                    if (error is java.net.SocketTimeoutException || error is java.net.ConnectException) getView()?.showMessage("No se pudo conectar con servidor")
                    if (error is NoConnectivityException) getView()?.showMessage("Sin conexión a internet")
                    try {
                        error as HttpException
                        val jObjError = JSONObject(error.response().errorBody()!!.string())
                        getView()?.showMessage(jObjError.getString("messages"))
                    } catch (e: Exception) {
                        getView()?.showMessage("Error al obtener datos.")
                    }
                    getView()?.loadProgress(false)
                })
    }

    override fun getPedido(idPedido: Int, client: ClientDTO) {
        //si el id del pedido es 0 quiere decir que es un nuevo pedido y no uno a modificar.
        if (idPedido == 0) {
            getProducts(client, null, idPedido)
        }else{
            //si es un pedido a modificar, le pido al interactor que traiga los id de los productos
            //para ese pedido.
            getProducts(client,interactor?.getPedidosProducts(idPedido,client.id), idPedido)
        }
    }

    override fun savePedido(pedido: Pedido) {
            doAsync {
                try {
                    //guardo los pedidos en la BD local (Sqlite), y le aviso a la View para que se cierre
                    interactor?.savePedido(pedido)
                    getView()?.close()
                } catch (e: Exception) {
                    throw e
                }
            }
    }
}