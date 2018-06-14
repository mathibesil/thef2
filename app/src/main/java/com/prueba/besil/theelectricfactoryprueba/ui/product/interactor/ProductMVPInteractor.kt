package com.prueba.besil.theelectricfactoryprueba.ui.product.interactor

import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.MVPInteractor
import io.reactivex.Observable

interface ProductMVPInteractor : MVPInteractor {
    fun getProducts(): Observable<List<ProductDTO>>
}