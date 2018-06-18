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
    //region var
    @Inject
    lateinit var presenter: ClientPresenter<ClientMVPView, ClientMVPInteractor>
    @Inject
    lateinit var adapter: ClientAdapter
    //endregion

    companion object {
        //devuelvo instancia del objeto y asigno valor a TAG para saber que fragmento es
        internal val TAG = "ClientFragment"
        fun newInstance(): ClientFragment = ClientFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_clients, container, false)
    }

    override fun setUp() {
        presenter.onAttach(this)
        if (isOnline()) { //verifico conexión a internet
            getBaseActivity()!!.toolbar_text.text = "Clientes"
            getBaseActivity()?.setSupportActionBar(toolbar_home)
            home_fragment_swiperefresh.setOnRefreshListener { //swipe para actualizar.
                if (!adapter.isLoading) {
                    adapter.isLoading = true //utilizo variable para no actualizar si ya esta actualizando.
                    presenter.getClients()
                } else swipeRefreshOff()
            }
            //region recycler
            val mGridLayoutManager = GridLayoutManager(this.context, 1)
            rvClients.setLayoutManager(mGridLayoutManager)
            rvClients.adapter = adapter
            adapter.clientInterface = this
            //endregion
            presenter.getClients() //le indico al presentador que cargue los clientes.
        } else { //si no tengo internet
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

    override fun updateClients(listClients: List<ClientDTO>) {//cargo los clientes que obtengo del presentador.
        adapter.clientDTOList = listClients
        adapter.isLoading = false
        adapter.notifyDataSetChanged()
    }

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

    override fun itemClicked(client: ClientDTO) { //capturo si en el adaptador se seleccionó un cliente.
            //inicio ventana de productos y le envío el cliente seleccionado
            val intent = Intent(activity, ProductActivity::class.java)
            intent.putExtra("client", client)
            startActivity(intent)
    }
}