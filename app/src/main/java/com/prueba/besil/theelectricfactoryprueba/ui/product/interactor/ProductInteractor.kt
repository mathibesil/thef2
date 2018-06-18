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
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.transaction
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import javax.inject.Inject

class ProductInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper) : BaseInteractor(preferenceHelper), ProductMVPInteractor {
    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var myDatabaseOpenHelper: MyDatabaseOpenHelper

    override fun savePedido(pedido: Pedido) {
        var idAuto: Long
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedDate = df.format(pedido.date)
        myDatabaseOpenHelper.use {
            transaction { //Inicio transacción por si falla.
                if(pedido.idPedido>0){
                    //si es un pedido existente, elimino e inserto los nuevos datos.
                    delete("pedidos_productos","id_pedido = {idPedido}", "idPedido" to pedido.idPedido)
                    delete("pedidos","id = {idPedido}", "idPedido" to pedido.idPedido)
                    idAuto = pedido.idPedido.toLong()
                            insert("pedidos",
                            "id" to pedido.idPedido,
                            "id_client" to pedido.client.id,
                            "date" to formattedDate)
                }else{
                    //Si es un pedido nuevo, luego de guardar obtengo el id auto genereado por la BD.
                    idAuto = insert("pedidos",
                            "id_client" to pedido.client.id,
                            "date" to formattedDate)
                }
                //por cada producto guardo para un id de producto, su id y cantidad en la BD
                for (pedidoProduct: Pedido.PedidoProduct in pedido.pedidoProductList) {
                    insert("pedidos_productos",
                            "id_pedido" to idAuto,
                            "id_product" to pedidoProduct.product.id,
                            "quantity" to pedidoProduct.quantity)
                }
            }
        }
     }

    override fun getProducts(): Observable<List<ProductDTO>> {
        //obtengo productos desde internet.
        return retrofit.create(ThefService::class.java).getProducts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPedidosProducts(idPedido: Int, idClient: Int): List<HashMap<String, Any>> {
        val pedidosProductsList = mutableListOf<HashMap<String, Any>>()
        myDatabaseOpenHelper.use {
            //Obtengo productos para un pedido en la BD, y creo un Hash con id del producto y su cantidad.
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