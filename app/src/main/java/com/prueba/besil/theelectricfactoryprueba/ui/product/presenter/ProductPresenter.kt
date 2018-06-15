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
        interactor?.getProducts()
                ?.subscribe({
                    val listProducts: MutableList<Pedido.PedidoProduct> = mutableListOf()
                    for (product: ProductDTO in it) {
                        var quantityTemp =0
                        if (pedidosProducts != null) {
                            for(hash:HashMap<String, Any> in pedidosProducts){
                                if(hash["idProduct"]==product.id){
                                    quantityTemp = hash["quantity"].toString().toInt()
                                }
                            }
                        }
                        listProducts.add(Pedido.PedidoProduct(product, quantityTemp))
                    }
                    val c = Calendar.getInstance().time
                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val formattedDate = df.format(c)
                    val date = df.parse(formattedDate)

                    getView()?.updateProducts(Pedido(idPedido, client, date , listProducts))
                    loadersOff()
                }, { error: Throwable ->
                    if (error is java.net.SocketTimeoutException || error is java.net.ConnectException) getView()?.showMessage("No se pudo conectar con servidor")
                    if (error is NoConnectivityException) getView()?.showMessage("Sin conexión a internet")
                    try {
                        error as HttpException
                        val jObjError = JSONObject(error.response().errorBody()!!.string())
                        getView()?.showMessage(jObjError.getString("messages"))
                    } catch (e: Exception) {
                        getView()?.showMessage("Error al obtener datos.")
                    }
                    loadersOff()
                })
    }

    override fun getPedido(idPedido: Int, client: ClientDTO) {
        if (idPedido == 0) {
            getProducts(client, null, idPedido)
        }else{
            getProducts(client,interactor?.getPedidosProducts(idPedido,client.id), idPedido)
        }
    }

    private fun loadersOff() {
        getView()?.loadProgress(false)
        getView()?.swipeRefreshOff()
    }

    override fun savePedido(pedido: Pedido) {
        doAsync {
            interactor?.savePedido(pedido)
            getView()?.close()
        }
    }
}