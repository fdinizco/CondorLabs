package com.example.androidtechnicaltest.ui.league

import com.example.androidtechnicaltest.ui.base.BaseState

sealed class LeagueState: BaseState {
    object Selected : LeagueState()
}