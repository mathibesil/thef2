package com.prueba.besil.theelectricfactoryprueba.ui.product

import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.presenter.ProductMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.presenter.ProductPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductMVPView
import dagger.Module
import dagger.Provides

@Module
class ProductActivityModule {

    @Provides
    internal fun provideProductInteractor(interactor: ProductInteractor): ProductMVPInteractor = interactor

    @Provides
    internal fun provideProductPresenter(presenter: ProductPresenter<ProductMVPView, ProductMVPInteractor>)
            : ProductMVPPresenter<ProductMVPView, ProductMVPInteractor> = presenter
}