package com.example.androidtechnicaltest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.example.androidtechnicaltest.R
import com.example.androidtechnicaltest.core.navigation.navigateToFragment
import com.example.androidtechnicaltest.extension.notNull
import com.example.androidtechnicaltest.ui.base.BaseActivity
import com.example.androidtechnicaltest.ui.category.CategoryState
import com.example.androidtechnicaltest.ui.category.SelectCategoryFragment
import com.example.androidtechnicaltest.ui.league.LeagueState
import com.example.androidtechnicaltest.ui.league.SelectLeagueFragment
import com.example.androidtechnicaltest.ui.loading.LoadingFragment
import com.example.androidtechnicaltest.ui.loading.LoadingState
import com.example.androidtechnicaltest.ui.team.SelectTeamFragment
import com.example.androidtechnicaltest.ui.team.TeamDetailFragment
import com.example.androidtechnicaltest.ui.team.TeamState

class MainActivity : BaseActivity() {

    private lateinit var categoryObserver: Observer<CategoryState>
    private lateinit var leagueObserver: Observer<LeagueState>
    private lateinit var teamObserver: Observer<TeamState>
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoadingFragment.newInstance())
                .commitNow()
        }
        setupEventObservers()
    }

    private fun setupEventObservers() {
        categoryObserver = Observer {
            it ?: return@Observer
            when (it) {
                is CategoryState.Available -> loadCategories()
                is CategoryState.Selected -> loadLeagues()
            }
        }
        mainViewModel.categoryState.observe(this, categoryObserver)

        leagueObserver= Observer {
            it ?: return@Observer
            when (it) {
                is LeagueState.Selected -> showLoadingFragment()
            }
        }
        mainViewModel.leagueState.observe(this, leagueObserver)

        teamObserver= Observer {
            it ?: return@Observer
            when (it) {
                is TeamState.Selected -> loadTeamInfo()
                is TeamState.Available -> loadTeams()
            }
        }
        mainViewModel.teamState.observe(this, teamObserver)
    }

    private fun loadCategories() {
        val originFragment = getVisibleFragment()
        originFragment.notNull {
            navigateToFragment(this, SelectCategoryFragment.newInstance())
        }
    }

    private fun loadLeagues() {
        val originFragment = getVisibleFragment()
        originFragment.notNull {
            navigateToFragment(this, SelectLeagueFragment.newInstance())
        }
    }

    private fun loadTeamInfo() {
        val originFragment = getVisibleFragment()
        originFragment.notNull {
            navigateToFragment(this, TeamDetailFragment.newInstance())
        }
    }

    private fun loadTeams() {
        val originFragment = getVisibleFragment()
        originFragment.notNull {
            navigateToFragment(this, SelectTeamFragment.newInstance(), addToBackStack = false)
        }
    }

    private fun showLoadingFragment(){
        val originFragment = getVisibleFragment()
        originFragment.notNull {
            navigateToFragment(this, LoadingFragment.newInstance(), addToBackStack = false)
        }
    }

    private fun getVisibleFragment(): Fragment? {
        val fragmentManager: FragmentManager = this.supportFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment.isVisible) return fragment
        }
        return null
    }
}