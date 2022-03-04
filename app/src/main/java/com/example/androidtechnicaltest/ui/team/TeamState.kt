package com.example.androidtechnicaltest.ui.team

import com.example.androidtechnicaltest.ui.base.BaseState

sealed class TeamState: BaseState {
    object Available : TeamState()
    object Selected : TeamState()
}
