package com.prueba.besil.theelectricfactoryprueba.ui.main

import com.prueba.besil.theelectricfactoryprueba.ui.main.interactor.MainInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.main.interactor.MainMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.main.presenter.MainMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.main.presenter.MainPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.main.view.MainMVPView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideMainInteractor(interactor: MainInteractor): MainMVPInteractor = interactor

    @Provides
    internal fun provideMainPresenter(presenter: MainPresenter<MainMVPView, MainMVPInteractor>)
            : MainMVPPresenter<MainMVPView, MainMVPInteractor> = presenter


}