package com.prueba.besil.theelectricfactoryprueba.di.module

import android.app.Application
import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.prueba.besil.theelectricfactoryprueba.data.network.util.AddAuthInterceptor
import com.prueba.besil.theelectricfactoryprueba.data.network.util.ConnectivityInterceptor
import com.prueba.besil.theelectricfactoryprueba.data.preferences.AppPreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.di.BaseUrl
import com.prueba.besil.theelectricfactoryprueba.di.PreferenceInfo
import com.prueba.besil.theelectricfactoryprueba.ui.client.view.ClientAdapter
import com.prueba.besil.theelectricfactoryprueba.ui.product.view.ProductAdapter
import com.prueba.besil.theelectricfactoryprueba.util.AppConstant
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun providePrefHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper

    @Provides
    @PreferenceInfo
    internal fun provideprefFileName(): String = AppConstant.PREF_NAME

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache, addAuthInterceptor: AddAuthInterceptor, connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(addAuthInterceptor)
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideAddAuthInterceptor(preferenceHelper: AppPreferenceHelper): AddAuthInterceptor {
        return AddAuthInterceptor(preferenceHelper)
    }

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(context: Context): ConnectivityInterceptor {
        return ConnectivityInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, @BaseUrl baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @BaseUrl
    internal fun provideBaseUrl(): String = AppConstant.BASEURL

    @Provides
    @Singleton
    internal fun provideClientAdapter(): ClientAdapter = ClientAdapter(ArrayList())

    @Provides
    @Singleton
    internal fun provideProductAdapter(): ProductAdapter = ProductAdapter()

}