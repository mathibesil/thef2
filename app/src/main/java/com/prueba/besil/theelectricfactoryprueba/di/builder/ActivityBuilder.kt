package com.prueba.besil.theelectricfactoryprueba.di.builder

import com.prueba.besil.theelectricfactoryprueba.ui.client.ClientFragmentModule
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientFragment
import com.prueba.besil.theelectricfactoryprueba.ui.main.MainActivityModule
import com.prueba.besil.theelectricfactoryprueba.ui.main.view.MainActivity
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.PedidoFragmentModule
import com.prueba.besil.theelectricfactoryprueba.ui.pedido.view.PedidoFragment
import com.prueba.besil.theelectricfactoryprueba.ui.product.ProductActivityModule
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(ClientFragmentModule::class)])
    abstract fun bindClientFragment(): ClientFragment

    @ContributesAndroidInjector(modules = [(ProductActivityModule::class)])
    abstract fun bindProductActivity(): ProductActivity

    @ContributesAndroidInjector(modules = [(PedidoFragmentModule::class)])
    abstract fun bindPedidoFragment(): PedidoFragment
}