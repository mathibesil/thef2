package com.prueba.besil.theelectricfactoryprueba.ui.client.presenter

import com.prueba.besil.theelectricfactoryprueba.data.network.util.NoConnectivityException
import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.BasePresenter
import com.prueba.besil.theelectricfactoryprueba.ui.client.interactor.ClientMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientMVPView
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class ClientPresenter <V: ClientMVPView, I: ClientMVPInteractor> @Inject internal constructor(interactor: I) : BasePresenter<V,I>(interactor), ClientMVPPresenter<V,I>{
    override fun getClients() {
        getView()?.loadProgress(true)
        interactor?.getClients() //los clientes que obtengo del interactor
                ?.subscribe({
                    //si esta ok emito la lista y la envío a la función de la interface, en la view.
                    getView()?.updateClients(it)
                    loadersOff()//oculto progress bar
                }, { error : Throwable ->
                    //en caso de error muestro en el view.
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

    private fun loadersOff() {
        getView()?.loadProgress(false)
        getView()?.swipeRefreshOff()
    }
}