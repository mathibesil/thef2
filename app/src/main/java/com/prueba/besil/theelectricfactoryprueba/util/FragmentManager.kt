package com.prueba.besil.theelectricfactoryprueba.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

internal fun FragmentManager.removeFragment(tag: String) {
    this.beginTransaction()
            .disallowAddToBackStack()
            .remove(this.findFragmentByTag(tag))
            .commitNow()
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.beginTransaction().disallowAddToBackStack()
            //.setCustomAnimations(slideIn, slideOut)
            .add(containerViewId, fragment, tag)
            .commit()
}
internal fun FragmentManager.showFragment(tag:String){
    this.beginTransaction()
           // .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .show(this.findFragmentByTag(tag))
            .commit()
}
internal fun FragmentManager.hideFragment(tag:String){
    if(!tag.equals("") && !tag.equals("me"))
    this.beginTransaction()
           // .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .hide(this.findFragmentByTag(tag))
            .commit()
}



