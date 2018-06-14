package com.prueba.besil.theelectricfactoryprueba.ui.main.presenter

import com.prueba.besil.theelectricfactoryprueba.ui.base.presenter.MVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.main.interactor.MainMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.main.view.MainMVPView

interface MainMVPPresenter<V:MainMVPView, I:MainMVPInteractor> : MVPPresenter<V,I>{

}