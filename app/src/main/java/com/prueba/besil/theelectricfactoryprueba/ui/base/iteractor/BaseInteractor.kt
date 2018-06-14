package com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor

import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.util.AppConstant

/**
 */
open class BaseInteractor(): MVPInteractor {
    protected lateinit var preferenceHelper: PreferenceHelper

    constructor(preferenceHelper: PreferenceHelper) : this() {
        this.preferenceHelper = preferenceHelper
    }
}