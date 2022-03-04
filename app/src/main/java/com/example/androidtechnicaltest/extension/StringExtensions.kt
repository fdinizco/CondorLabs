package com.example.androidtechnicaltest.extension

import com.example.androidtechnicaltest.util.QUERY_SEPARATOR
import com.example.androidtechnicaltest.util.URL_SEPARATOR
import com.example.androidtechnicaltest.util.WHITE_SPACE

/**
 * Build REQUEST URL
 *
 * @return the REQUEST URL
 */
fun String.buildURL(): String {
    // split the string by spaces in list
    return this.replace(WHITE_SPACE, URL_SEPARATOR)
}

/**
 * Build REQUEST QUERY
 *
 * @return the REQUEST QUERY
 */
fun String.buildQuery(): String {
    // split the string by spaces in list
    return this.replace(WHITE_SPACE, QUERY_SEPARATOR)
}

/**
 * Build STRING
 *
 * @return the STRING or DEFAULT
 */
fun Any?.ifNullOrEmpty(default: String) =
    if (this == null || (this is CharSequence && this.isEmpty()))
        default
    else
        this.toString()