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
        for(hash : HashMap<String, Any> in interactor?.getPedidos()!!){
            haveData = true
            interactor?.getClient(hash["idClient"].toString().toInt())
            ?.subscribe({
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val myDate = simpleDateFormat.parse(hash["date"].toString())
                val pedido = Pedido(hash["idPedido"].toString().toInt(),it, myDate, listOf())
                getView()?.updatePedidos(pedido)
                loadersOff()
            }, { error : Throwable ->
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
        if(!haveData)
            getView()?.showNoData(true)
        loadersOff()
    }

    private fun loadersOff() {
        getView()?.loadProgress(false)
        getView()?.swipeRefreshOff()
    }
}