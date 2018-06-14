package com.prueba.besil.theelectricfactoryprueba.data.network.util

import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AddAuthInterceptor @Inject internal constructor(var preferenceHelper: PreferenceHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (preferenceHelper.getToken() != "") builder.addHeader("Authorization", preferenceHelper.getToken())
        return chain.proceed(builder.build())
    }
}