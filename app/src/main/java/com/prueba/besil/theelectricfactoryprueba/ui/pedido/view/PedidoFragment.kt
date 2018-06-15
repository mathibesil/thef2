package com.prueba.besil.theelectricfactoryprueba.ui.pedido.view


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
import javax.inject.Inject


class PedidoFragment : BaseFragment(), PedidoMVPView {
    //region vars
    @Inject
    lateinit var presenter: PedidoPresenter<PedidoMVPView, PedidoMVPInteractor>
    @Inject
    lateinit var adapter: PedidoAdapter

    //endregion
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pedidos, container, false)
    }

    override fun setUp() {
        presenter.onAttach(this)
        if (isOnline()) {
            setUpStart()
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

    private fun setUpStart() {
        //region RecyclerView
        val mGridLayoutManager = GridLayoutManager(this.context, 1)
        rvPedidos.setLayoutManager(mGridLayoutManager)
        rvPedidos.adapter = adapter
        home_fragment_swiperefresh.setOnRefreshListener {
            if (!adapter.isLoading) {
                adapter.pedidoList= mutableListOf()
                adapter.isLoading = true
                presenter.getPedidos()
            } else swipeRefreshOff()
        }
        //endregion
        getBaseActivity()!!.toolbar_text.text = getString(R.string.toolbarTitle)
        getBaseActivity()?.setSupportActionBar(toolbar_home)
        adapter.pedidoInterface = this
        adapter.pedidoList= mutableListOf()
        presenter.getPedidos()
    }

    companion object {
        internal val TAG = "PedidoFragment"
        fun newInstance(): PedidoFragment = PedidoFragment()
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    protected fun isOnline(): Boolean {
        val cm = getBaseActivity()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    //region LoadData

    override fun updatePedidos(pedido: Pedido) {
        adapter.pedidoList.add(pedido)
        adapter.pedidoList = (adapter.pedidoList.toList().sortedWith(compareBy(Pedido::date))).toMutableList()
        adapter.pedidoInterface = this
        adapter.isLoading = false
        adapter.notifyDataSetChanged()
    }
    //endregion

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

    override fun scrollToTop() {
        rvPedidos.smoothScrollToPosition(0)
    }

    override fun blockUi() {
    }

    override fun unBlockUi() {
    }

    override fun itemClicked(pedido: Pedido, client:ClientDTO) {
        val intent = Intent(activity, ProductActivity::class.java)
        intent.putExtra("idPedido", pedido.idPedido)
        intent.putExtra("client", client)
        startActivity(intent)
    }
}