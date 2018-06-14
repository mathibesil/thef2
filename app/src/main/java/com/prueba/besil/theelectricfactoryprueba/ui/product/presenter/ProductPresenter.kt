package com.prueba.besil.theelectricfactoryprueba.ui.product.presenter

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.util.NoConnectivityException
import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.BasePresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductMVPView
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProductPresenter<V : ProductMVPView, I : ProductMVPInteractor> @Inject internal constructor(interactor: I) : BasePresenter<V, I>(interactor), ProductMVPPresenter<V, I> {
    fun getProducts(client: ClientDTO) {
        interactor?.getProducts()
                ?.subscribe({
                    val listProducts : MutableList<Pedido.PedidoProduct> = mutableListOf()
                    for(product: ProductDTO in it){
                        listProducts.add(Pedido.PedidoProduct(product,0))
                    }
                    getView()?.updateProducts(Pedido(0, client, Date(), listProducts))
                }, { error : Throwable ->
                    if (error is java.net.SocketTimeoutException || error is java.net.ConnectException) getView()?.showMessage("No se pudo conectar con servidor")
                    if (error is NoConnectivityException) getView()?.showMessage("Sin conexión a internet")
                    try {
                        error as HttpException
                        val jObjError = JSONObject(error.response().errorBody()!!.string())
                        getView()?.showMessage(jObjError.getString("messages"))
                    } catch (e: Exception) {
                        getView()?.showMessage("Error al obtener datoes.")
                    }
                    loadersOff()
                })
    }
    override fun getPedido(idPedido: Int, client: ClientDTO){
        if(idPedido==0){
            getProducts(client)
        }
    }
    private fun loadersOff() {
        getView()?.loadProgress(false)
        getView()?.swipeRefreshOff()
    }
}