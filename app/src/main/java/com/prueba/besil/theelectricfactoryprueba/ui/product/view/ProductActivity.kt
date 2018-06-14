package com.prueba.besil.theelectricfactoryprueba.ui.product.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ProductDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.BaseActivity
import com.prueba.besil.theelectricfactoryprueba.ui.product.interactor.ProductMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.product.presenter.ProductMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.util.removeFragment
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.fragment_clients.*
import javax.inject.Inject


class ProductActivity : BaseActivity(), ProductMVPView {
    //region vars
    @Inject
    lateinit var presenter: ProductMVPPresenter<ProductMVPView, ProductMVPInteractor>
    @Inject
    lateinit var adapter: ProductAdapter

    private var idPedido: Int = 0
    private lateinit var client: ClientDTO
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        presenter.onAttach(this)
//        if(intent.getStringExtra("pedido") != null)
//            idPedido = intent.getStringExtra("pedido").toInt()
        client = intent.extras.getSerializable("client") as ClientDTO
        val mGridLayoutManager = GridLayoutManager(this, 1)
        rvProducts.setLayoutManager(mGridLayoutManager)
        rvProducts.adapter = adapter
        presenter.getPedido(idPedido, client)

    }

    override fun updateProducts(pedido: Pedido) {
        adapter.listPedidos = pedido.pedidoProductList
        adapter.isLoading = false
        adapter.notifyDataSetChanged()
    }

    override fun onFragmentDetached(tag: String) {
    }
    override fun swipeRefreshOff() {
       // home_fragment_swiperefresh.isRefreshing = false
    }
    override fun blockUi() {

    }

    override fun unBlockUi() {

    }

    override fun onFragmentAttached() {
    }
    override fun showMessage(texto: String) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
    }

    override fun loadProgress(enabled: Boolean) {
        if (enabled)
            pbProducts.visibility = View.VISIBLE
        else
            pbProducts.visibility = View.GONE
    }

}