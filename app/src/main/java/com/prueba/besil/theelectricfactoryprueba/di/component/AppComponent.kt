package com.prueba.besil.theelectricfactoryprueba.di.component

import android.app.Application
import com.prueba.besil.theelectricfactoryprueba.TheElectricFactoryApp
import com.prueba.besil.theelectricfactoryprueba.di.builder.ActivityBuilder
import com.prueba.besil.theelectricfactoryprueba.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,AppModule::class,
        ActivityBuilder::class))

interface AppComponent {

    @Component.Builder
        interface Builder{
            @BindsInstance fun application(app: Application): Builder
            fun build(): AppComponent
    }

    fun inject(app: TheElectricFactoryApp)
}