package com.prueba.besil.theelectricfactoryprueba.ui.client.interactor

import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.ThefService
import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class ClientInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper) : BaseInteractor(preferenceHelper), ClientMVPInteractor {
    @Inject
    lateinit var retrofit: Retrofit

    override fun getClients(): Observable<List<ClientDTO>> {
        return retrofit.create(ThefService::class.java).getClients().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

}