package com.prueba.besil.theelectricfactoryprueba.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.prueba.besil.theelectricfactoryprueba.di.PreferenceInfo


import com.prueba.besil.theelectricfactoryprueba.util.AppConstant
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(context: Context, @PreferenceInfo prefFileName: String) : PreferenceHelper {
    override fun getToken(): String = mpref.getString(Token, "ASD")

    override fun setToken(texto: String?) {
        mpref.edit().putString(Token, texto).apply()
    }

    companion object {
        private val Token = "Token"
    }

    private var mpref: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

}