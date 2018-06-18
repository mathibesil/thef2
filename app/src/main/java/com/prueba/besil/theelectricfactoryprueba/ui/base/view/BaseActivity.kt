package com.prueba.besil.theelectricfactoryprueba.ui.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import dagger.android.AndroidInjection

/**
 */
abstract class BaseActivity: AppCompatActivity(), MVPView, BaseFragment.CallBack{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDi()
    }

    private fun performDi() = AndroidInjection.inject(this)

    override fun showMessage(texto: String){
        Toast.makeText(this,texto, Toast.LENGTH_LONG).show()
    }
}