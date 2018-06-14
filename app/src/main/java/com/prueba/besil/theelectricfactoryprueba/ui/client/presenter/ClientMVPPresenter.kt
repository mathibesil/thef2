package com.prueba.besil.theelectricfactoryprueba.ui.client.presenter

import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.MVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.client.interactor.ClientMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientMVPView


interface ClientMVPPresenter<V: ClientMVPView,I:ClientMVPInteractor> : MVPPresenter<V,I>{
    fun getClients()
}