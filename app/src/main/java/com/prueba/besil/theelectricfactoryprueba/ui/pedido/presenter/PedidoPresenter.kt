package com.prueba.besil.theelectricfactoryprueba.ui.pedido.presenter

import android.annotation.SuppressLint
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.util.NoConnectivityException
import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.BasePresenter
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor.PedidoMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoMVPView
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class PedidoPresenter<V : PedidoMVPView, I : PedidoMVPInteractor> @Inject internal constructor(interactor: I) : BasePresenter<V, I>(interactor), PedidoMVPPresenter<V, I> {
    @SuppressLint("SimpleDateFormat")
    override fun getPedidos() {
        var haveData = false
        //cargo pedidos de la BD local
        for(hash : HashMap<String, Any> in interactor?.getPedidos()!!){
            getView()?.loadProgress(true)
            haveData = true
            interactor?.getClient(hash["idClient"].toString().toInt()) //cargo información del cliente desde internet
            ?.subscribe({
                //al obtener información del cliente, creo un pedido y lo devuelvo al a view.
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val myDate = simpleDateFormat.parse(hash["date"].toString())
                val pedido = Pedido(hash["idPedido"].toString().toInt(),it, myDate, listOf())
                getView()?.updatePedidos(pedido)
                loadersOff()
            }, { error : Throwable ->
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
                loadersOff()
            })
        }
        if(!haveData) // si no tengo pedidos en la BD local, le indico a la view para que muestre mensaje.
            getView()?.showNoData(true)
        loadersOff()
    }

    private fun loadersOff() {
        getView()?.loadProgress(false)
        getView()?.swipeRefreshOff()
    }
}