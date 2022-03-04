package com.example.androidtechnicaltest.ui.loading

import com.example.androidtechnicaltest.ui.base.BaseState

/**
 * Sealed class to define Events on View Model that View should react to it
 */
sealed class LoadingState : BaseState {
    object OnRetrieving : LoadingState()
    object OnError : LoadingState()
    object OnSuccess : LoadingState()
}