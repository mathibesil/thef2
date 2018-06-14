package com.prueba.besil.theelectricfactoryprueba.data.network.util

import java.io.IOException

class NoConnectivityException : IOException() {
    companion object {
        fun getMessage(): String {
            return "No connectivity exception"
        }
    }
}