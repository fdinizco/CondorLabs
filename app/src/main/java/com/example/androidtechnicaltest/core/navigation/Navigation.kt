package com.example.androidtechnicaltest.core.navigation

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.androidtechnicaltest.extension.notNull


fun navigateToActivity(context: Context, targetActivity: Class<*>, intentFlag: Int? = null, bundleOptions: Bundle? = null){
    val intent = Intent(context, targetActivity)
    intentFlag.notNull { intent.addFlags(this) }
    startActivity(context, intent, bundleOptions)
}

fun navigateToFragment(sourceFragment: Fragment, targetFragment: Fragment, addToBackStack: Boolean = true) {
    val backStack = sourceFragment.javaClass.name
    val transaction = sourceFragment.requireActivity().supportFragmentManager.beginTransaction()
    transaction.replace(sourceFragment.id, targetFragment)
    if (addToBackStack) {
        transaction.addToBackStack(backStack)
    }
    transaction.commit()
}