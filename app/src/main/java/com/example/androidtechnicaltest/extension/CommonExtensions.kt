package com.example.androidtechnicaltest.extension

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.example.androidtechnicaltest.ui.main.MainActivity


/**
 *  Check if object or instance is not null
 */
inline fun <T> T?.notNull(block: T.() -> Unit): T? {
    this?.block()
    return this@notNull
}

/**
 *  Check if object or instance is null
 */
inline fun <T> T?.isNull(block: T?.() -> Unit): T? {
    if (this == null) block()
    return this@isNull
}


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }