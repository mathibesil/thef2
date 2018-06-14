package com.prueba.besil.theelectricfactoryprueba.ui.base.view

interface MVPView {
    fun showProgress()
    fun hideProgress()
    fun showMessage(texto: String)
    fun blockUi()
    fun unBlockUi()
}
