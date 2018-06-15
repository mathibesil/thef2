package com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.ThefService
import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.data.sqlite.MyDatabaseOpenHelper
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.*
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class PedidoInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper) : BaseInteractor(preferenceHelper), PedidoMVPInteractor {
    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var myDatabaseOpenHelper: MyDatabaseOpenHelper

    override fun getPedidos(): List<HashMap<String, Any>> {
        val pedidosList = mutableListOf<HashMap<String, Any>>()
        myDatabaseOpenHelper.use {
            select("pedidos").exec {
                while (this.moveToNext()) {
                    val hashMap = HashMap<String, Any>()
                    hashMap["idPedido"] = this.getInt(getColumnIndex("id"))
                    hashMap["idClient"] = this.getInt(getColumnIndex("id_client"))
                    hashMap["date"] = this.getString(getColumnIndex("date"))
                    pedidosList.add(hashMap)
                }
            }
        }
        return pedidosList
    }

    override fun getClient(idClient: Int): Observable<ClientDTO> {
        return retrofit.create(ThefService::class.java).getClientById(idClient).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

}