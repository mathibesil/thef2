package com.prueba.besil.theelectricfactoryprueba.data.preferences

import com.prueba.besil.theelectricfactoryprueba.util.AppConstant

interface PreferenceHelper {
    fun setToken(texto: String?)
    fun getToken(): String
}