package com.prueba.besil.theelectricfactoryprueba.ui.product.interactor

import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.ThefService
import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.data.sqlite.MyDatabaseOpenHelper
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.BaseInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.concurrent.Future
import javax.inject.Inject

class ProductInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper) : BaseInteractor(preferenceHelper), ProductMVPInteractor{
    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var myDatabaseOpenHelper: MyDatabaseOpenHelper
    override fun savePedido(pedido: Pedido){
            var idAuto=0L
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = df.format(pedido.date)
            myDatabaseOpenHelper.use { idAuto = insert("pedidos",
                    "id_client" to pedido.client.id,
                    "date" to formattedDate)}
            for(pedidoProduct:Pedido.PedidoProduct in pedido.pedidoProductList){
                myDatabaseOpenHelper.use { insert("pedidos_productos",
                        "id_pedido" to idAuto,
                        "id_product" to pedidoProduct.product.id,
                        "quantity" to pedidoProduct.quantity)}
        }
    }
    override fun getProducts(): Observable<List<ProductDTO>> {
        return retrofit.create(ThefService::class.java).getProducts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }
    override fun getPedidosProducts(idPedido:Int, idClient:Int): List<HashMap<String, Any>> {
        val pedidosProductsList = mutableListOf<HashMap<String, Any>>()
        myDatabaseOpenHelper.use {
            select("pedidos_productos").whereArgs("(id_pedido = {idPedido})",
                    "idPedido" to idPedido).exec {
                while (this.moveToNext()) {
                    val hashMap = HashMap<String, Any>()
                    hashMap["idProduct"] = this.getInt(getColumnIndex("id_product"))
                    hashMap["quantity"] = this.getInt(getColumnIndex("quantity"))
                    pedidosProductsList.add(hashMap)
                }
            }
        }
        return pedidosProductsList
    }
}