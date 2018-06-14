package com.prueba.besil.theelectricfactoryprueba.ui.main.interactor

import com.prueba.besil.theelectricfactoryprueba.data.preferences.PreferenceHelper
import com.prueba.besil.theelectricfactoryprueba.ui.base.iteractor.BaseInteractor
import javax.inject.Inject

class MainInteractor @Inject internal constructor(preferenceHelper: PreferenceHelper) : BaseInteractor(preferenceHelper), MainMVPInteractor{

}