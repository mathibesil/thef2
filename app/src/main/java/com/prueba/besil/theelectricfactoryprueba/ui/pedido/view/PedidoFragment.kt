package com.prueba.besil.theelectricfactoryprueba.ui.pedido.view


import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.classes.Pedido
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.BaseFragment
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.interactor.PedidoMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.presenter.PedidoPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pedidos.*
import java.util.ArrayList
import javax.inject.Inject


class PedidoFragment : BaseFragment(), PedidoMVPView {
    //region vars
    @Inject
    lateinit var presenter: PedidoPresenter<PedidoMVPView, PedidoMVPInteractor>
    @Inject
    lateinit var adapter: PedidoAdapter
    //endregion

    companion object {
        //devuelvo instancia del objeto y asigno valor a TAG para saber que fragmento es
        internal val TAG = "PedidoFragment"
        fun newInstance(): PedidoFragment = PedidoFragment()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pedidos, container, false)
    }

    override fun setUp() {
        presenter.onAttach(this)
        if (isOnline()) {//verifico conexión a internet
            getBaseActivity()!!.toolbar_text.text = "Pedidos"
            getBaseActivity()?.setSupportActionBar(toolbar_home)
            home_fragment_swiperefresh.setOnRefreshListener {//swipe para actualizar.
                if (!adapter.isLoading) {
                    adapter.pedidoList= mutableListOf()
                    adapter.isLoading = true//utilizo variable para no actualizar si ya esta actualizando.
                    presenter.getPedidos()
                } else swipeRefreshOff()
            }
            //region Recycler
            val mGridLayoutManager = GridLayoutManager(this.context, 1)
            rvPedidos.setLayoutManager(mGridLayoutManager)
            rvPedidos.adapter = adapter
            adapter.pedidoInterface = this
            adapter.pedidoList= mutableListOf()
            //endregion
            presenter.getPedidos()//le indico al presentador que cargue los pedidos.
        } else {
            AlertDialog.Builder(this.context!!)
                    .setTitle("Se necesita internet")
                    .setMessage("La aplicación necesita internet para poder funcionar")
                    .setCancelable(false)
                    .setNegativeButton("Salir", DialogInterface.OnClickListener { _, _ ->
                        getBaseActivity()?.finish()
                    })
                    .create()
                    .show()
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    protected fun isOnline(): Boolean {
        val cm = getBaseActivity()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    override fun updatePedidos(pedido: Pedido) { //cargo los pedidos que obtengo del presentador.
        adapter.pedidoList.add(pedido)
        //ordeno los pedidos por fecha descendente
        adapter.pedidoList = (adapter.pedidoList.toList().sortedWith(compareByDescending(Pedido::date))).toMutableList()
        adapter.pedidoInterface = this
        adapter.isLoading = false
        adapter.notifyDataSetChanged()
        if(adapter.pedidoList.size==0) // si no tengo pedidos muestro mensaje
            showNoData(true)
        else
            showNoData(false)
    }

    override fun showMessage(texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
    }

    override fun loadProgress(enabled: Boolean) {
        if (enabled)
            pbPedidos.visibility = View.VISIBLE
        else
            pbPedidos.visibility = View.GONE
    }

    override fun swipeRefreshOff() {
        home_fragment_swiperefresh.isRefreshing = false
    }

    override fun itemClicked(pedido: Pedido, client:ClientDTO) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("idPedido", pedido.idPedido)
        intent.putExtra("client", client)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
      if(requestCode==1) {
          if (resultCode == Activity.RESULT_OK) {
              adapter.pedidoList = mutableListOf()
              presenter.getPedidos()
          }
      }
    }
    override fun showNoData(enabled: Boolean) {
        if(enabled)
            txtNoData.visibility = View.VISIBLE
        else
            txtNoData.visibility = View.GONE
    }
}