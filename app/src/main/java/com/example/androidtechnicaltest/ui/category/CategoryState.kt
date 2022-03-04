package com.example.androidtechnicaltest.ui.category

import com.example.androidtechnicaltest.ui.base.BaseState

sealed class CategoryState : BaseState {
    object Selected : CategoryState()
    object Available : CategoryState()
}
