package com.prueba.besil.theelectricfactoryprueba.ui.main.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.sqlite.MyDatabaseOpenHelper
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.BaseActivity
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientFragment
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoFragment
import com.prueba.besil.theelectricfactoryprueba.util.addFragment
import com.prueba.besil.theelectricfactoryprueba.util.removeFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMVPView, HasSupportFragmentInjector {
    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    internal lateinit var myDatabaseOpenHelper: MyDatabaseOpenHelper
    private var actualFragment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainNavigationn.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mainNavigationn.selectedItemId = R.id.navigation_client
        setSupportActionBar(toolbar_home)
        createTables() //Se crean las tablas si no existen.
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    private fun createTables() {
        myDatabaseOpenHelper.readableDatabase.createTable("pedidos", true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "id_client" to INTEGER + NOT_NULL,
                "date" to TEXT)
        myDatabaseOpenHelper.readableDatabase.createTable("pedidos_productos", true,
                "id_pedido" to SqlType.create("INTEGER NOT_NULL PRIMARY_KEY"),
                "id_product" to SqlType.create("INTEGER NOT_NULL PRIMARY_KEY"),
                "quantity" to INTEGER + NOT_NULL,
                FOREIGN_KEY("id_pedido", "pedidos", "id"))
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
        //Al seleccionar pestaña cliente, oculto el fragmento actual, muestro toolbar, e inicio el fragmento cliente.
            R.id.navigation_client -> {
                appBarLayout_home.setExpanded(true, true)
                if (actualFragment != ClientFragment.TAG) {
                    if (actualFragment != "") supportFragmentManager.removeFragment(actualFragment)
                    actualFragment = ClientFragment.TAG
                    val newClientFragment = ClientFragment.newInstance()
                    supportFragmentManager.addFragment(R.id.cl_root_view, newClientFragment, ClientFragment.TAG)
                }
                return@OnNavigationItemSelectedListener true
            }
        //Al seleccionar pestaña pedidos, oculto el fragmento actual, muestro toolbar, e inicio el fragmento pedidos.
            R.id.navigation_pedido -> {
                appBarLayout_home.setExpanded(true, true)
                if (actualFragment != PedidoFragment.TAG) {
                    if (actualFragment != "") supportFragmentManager.removeFragment(actualFragment)
                    actualFragment = PedidoFragment.TAG
                    val newPedidoFragment = PedidoFragment.newInstance()
                    supportFragmentManager.addFragment(R.id.cl_root_view, newPedidoFragment, PedidoFragment.TAG)
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentDetached(tag: String) {
        supportFragmentManager?.removeFragment(tag = tag)
    }

    override fun onFragmentAttached() {
    }
}