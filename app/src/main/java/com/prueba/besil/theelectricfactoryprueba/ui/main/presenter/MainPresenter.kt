package com.prueba.besil.theelectricfactoryprueba.ui.main.presenter

import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.BasePresenter
import com.prueba.besil.theelectricfactoryprueba.ui.main.interactor.MainMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.main.view.MainMVPView
import javax.inject.Inject

class MainPresenter<V : MainMVPView, I : MainMVPInteractor> @Inject internal constructor(interactor: I) : BasePresenter<V, I>(interactor), MainMVPPresenter<V, I> {

}