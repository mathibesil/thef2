package com.prueba.besil.theelectricfactoryprueba.ui.client

import android.support.v7.widget.LinearLayoutManager
import com.prueba.besil.theelectricfactoryprueba.ui.client.interactor.ClientInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.client.interactor.ClientMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.client.presenter.ClientMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.client.presenter.ClientPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientFragment
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientMVPView
import dagger.Module
import dagger.Provides

@Module
class ClientFragmentModule {

    @Provides
    internal fun provideHomeInteractor(interactor: ClientInteractor): ClientMVPInteractor = interactor

    @Provides
    internal fun provideHomePresenter(presenter: ClientPresenter<ClientMVPView, ClientMVPInteractor>)
            : ClientMVPPresenter<ClientMVPView, ClientMVPInteractor> = presenter

    @Provides
    internal fun provideLinearLayoutManager(fragment: ClientFragment): LinearLayoutManager = LinearLayoutManager(fragment.activity)

}