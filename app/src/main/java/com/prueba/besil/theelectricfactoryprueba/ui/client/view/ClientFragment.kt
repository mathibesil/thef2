package com.prueba.besil.theelectricfactoryprueba.ui.client.view


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
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.BaseFragment
import com.prueba.besil.theelectricfactoryprueba.ui.client.interactor.ClientMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.client.presenter.ClientPresenter
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_clients.*
import javax.inject.Inject


class ClientFragment : BaseFragment(), ClientMVPView {
    //region vars
    @Inject
    lateinit var presenter: ClientPresenter<ClientMVPView, ClientMVPInteractor>
    @Inject
    lateinit var adapter: ClientAdapter

    //endregion
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_clients, container, false)
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
        rvClients.setLayoutManager(mGridLayoutManager)
        rvClients.adapter = adapter
        home_fragment_swiperefresh.setOnRefreshListener {
            if (!adapter.isLoading) {
                adapter.isLoading = true
                presenter.getClients()
            } else swipeRefreshOff()
        }
        //endregion
        getBaseActivity()!!.toolbar_text.text = getString(R.string.toolbarTitle)
        getBaseActivity()?.setSupportActionBar(toolbar_home)
        presenter.getClients()
    }

    companion object {
        internal val TAG = "ClientFragment"
        fun newInstance(): ClientFragment = ClientFragment()
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
    override fun firstLoadRestaurantes() {
        presenter.getClients()
    }

    override fun updateClients(listClients: List<ClientDTO>) {
        adapter.clientDTOList = listClients
        adapter.isLoading = false
        adapter.notifyDataSetChanged()
    }
    //endregion

    override fun showMessage(texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
    }

    override fun loadProgress(enabled: Boolean) {
        if (enabled)
            pbClients.visibility = View.VISIBLE
        else
            pbClients.visibility = View.GONE
    }

    override fun swipeRefreshOff() {
        home_fragment_swiperefresh.isRefreshing = false
    }

    override fun scrollToTop() {
        rvClients.smoothScrollToPosition(0)
    }

    override fun blockUi() {
    }

    override fun unBlockUi() {
    }

    override fun itemClicked(client: ClientDTO) {
        val intent = Intent(context, ProductActivity::class.java)
        intent.putExtra("client", client)
        startActivity(intent)
    }
}