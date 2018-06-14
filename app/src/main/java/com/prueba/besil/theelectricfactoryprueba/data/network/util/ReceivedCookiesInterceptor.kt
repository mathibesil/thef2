package com.prueba.besil.theelectricfactoryprueba.data.network.util

import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class ReceivedCookiesInterceptor @Inject internal constructor(var preferenceHelper: PreferenceHelper) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = ArrayList<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                var header2 = header.replace("]", "")
                header2 = header2.replace("[", "")
                cookies.add(header2)
            }
            // preferenceHelper.setCookie(cookies.get(0))
        }

        return originalResponse
    }
}