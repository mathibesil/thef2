package com.prueba.besil.theelectricfactoryprueba.ui.pedido

import android.support.v7.widget.LinearLayoutManager
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor.PedidoInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor.PedidoMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.presenter.PedidoMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.presenter.PedidoPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoFragment
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoMVPView
import dagger.Module
import dagger.Provides

@Module
class PedidoFragmentModule {

    @Provides
    internal fun provideHomeInteractor(interactor: PedidoInteractor): PedidoMVPInteractor = interactor

    @Provides
    internal fun provideHomePresenter(presenter: PedidoPresenter<PedidoMVPView, PedidoMVPInteractor>)
            : PedidoMVPPresenter<PedidoMVPView, PedidoMVPInteractor> = presenter

    @Provides
    internal fun provideLinearLayoutManager(fragment: PedidoFragment): LinearLayoutManager = LinearLayoutManager(fragment.activity)

}