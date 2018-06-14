package com.prueba.besil.theelectricfactoryprueba.ui.main.view

import android.location.Location
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.prueba.besil.theelectricfactoryprueba.R
import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.BaseActivity
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientFragment
import com.prueba.besil.theelectricfactoryprueba.ui.main.interactor.MainMVPInteractor
import com.prueba.besil.theelectricfactoryprueba.ui.main.presenter.MainMVPPresenter
import com.prueba.besil.theelectricfactoryprueba.util.addFragment
import com.prueba.besil.theelectricfactoryprueba.util.hideFragment
import com.prueba.besil.theelectricfactoryprueba.util.removeFragment
import com.prueba.besil.theelectricfactoryprueba.util.showFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMVPView, HasSupportFragmentInjector {
    //region vars
    @Inject
    lateinit var presenter: MainMVPPresenter<MainMVPView, MainMVPInteractor>
    @Inject
    internal lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private var actualFragment: String = ""
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainNavigationn.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mainNavigationn.selectedItemId = R.id.navigation_home
        presenter.onAttach(this)
        setSupportActionBar(toolbar_home)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar_home.visibility = View.VISIBLE
                appBarLayout_home.setExpanded(true, true)
                if (actualFragment.equals(ClientFragment.TAG)) {
                    val f: ClientFragment = supportFragmentManager.findFragmentByTag(ClientFragment.TAG) as ClientFragment
                    f.scrollToTop()
                } else {
                    supportFragmentManager.hideFragment(actualFragment)
                    actualFragment = ClientFragment.TAG
                    if (supportFragmentManager.findFragmentByTag(ClientFragment.TAG) == null) {
                        val newHomeFragment = ClientFragment.newInstance()
                        supportFragmentManager.addFragment(R.id.cl_root_view, newHomeFragment, ClientFragment.TAG)
                    } else
                        supportFragmentManager.showFragment(ClientFragment.TAG)
                }
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_notifications2 -> {
//                if (!actualFragment.equals(MapFragment.TAG)) {
//                    supportFragmentManager.hideFragment(actualFragment)
//                    toolbar_home.visibility = View.GONE
//                    actualFragment = MapFragment.TAG
//                    if (supportFragmentManager.findFragmentByTag(MapFragment.TAG) == null) {
//                        val newMapFragment = MapFragment.newInstance()
//                        supportFragmentManager.addFragment(R.id.cl_root_view, newMapFragment, MapFragment.TAG)
//                        newMapFragment.setData(datoes, actualLocation)
//                    } else
//                        supportFragmentManager.showFragment(MapFragment.TAG)
//                }
//                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentDetached(tag: String) {
        supportFragmentManager?.removeFragment(tag = tag)
    }

    override fun blockUi() {

    }

    override fun unBlockUi() {

    }

    override fun onFragmentAttached() {
    }
}