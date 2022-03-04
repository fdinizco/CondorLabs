package com.example.androidtechnicaltest.ui.main

import androidx.lifecycle.*
import com.example.androidtechnicaltest.domain.base.league.GetLeagueListUseCase
import com.example.androidtechnicaltest.domain.base.team.GetTeamListUseCase
import com.example.androidtechnicaltest.extension.default
import com.example.androidtechnicaltest.extension.notNull
import com.example.androidtechnicaltest.repository.league.LeagueInfo
import com.example.androidtechnicaltest.repository.team.TeamInfo
import com.example.androidtechnicaltest.service.content.ContentService
import com.example.androidtechnicaltest.ui.category.CategoryState
import com.example.androidtechnicaltest.ui.league.LeagueState
import com.example.androidtechnicaltest.ui.loading.LoadingState
import com.example.androidtechnicaltest.ui.team.TeamState
import com.example.androidtechnicaltest.util.EMPTY_STRING
import com.example.androidtechnicaltest.util.onFailure
import com.example.androidtechnicaltest.util.onSuccess


class MainViewModel  : ViewModel() {

    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    val categoryState: MutableLiveData<CategoryState> = MutableLiveData()
    val leagueState: MutableLiveData<LeagueState> = MutableLiveData()
    val teamState: MutableLiveData<TeamState> = MutableLiveData()

    //region LeagueList
    private val mutableLeagueList: MutableLiveData<ArrayList<LeagueInfo>> = MutableLiveData()
    val leagueList: LiveData<ArrayList<LeagueInfo>> = mutableLeagueList
    //endregion

    //region Category
    private val mutableCategoryList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val categoryList: LiveData<ArrayList<String>> = mutableCategoryList
    private var selectedCategory: String = EMPTY_STRING

    //endregion

    //region League by Category
    private val mutableLeagueOnCategoryList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val leagueOnCategoryList: LiveData<ArrayList<String>> = mutableLeagueOnCategoryList
    private var selectedLeague: String = EMPTY_STRING
    //endregion

    //region Team by League
    private val mutableTeamList: MutableLiveData<ArrayList<TeamInfo>> = MutableLiveData()
    val teamList: LiveData<ArrayList<TeamInfo>> = mutableTeamList
    val selectedTeam = MutableLiveData<TeamInfo?>().default(null)
    //endregion

    init {
        updateLoadingState(LoadingState.OnRetrieving)
        getLeagueList()
    }

    private fun getLeagueList() {
        val service = ContentService()
        val getLeagueListUseCase = GetLeagueListUseCase(service)
        getLeagueListUseCase.invoke(viewModelScope, null) {
            it.onFailure { updateLoadingState(LoadingState.OnError) }
            it.onSuccess { list ->
                updateGeneralInformation(list.leagues)
                updateLoadingState(LoadingState.OnSuccess)
                updateCategoryState(CategoryState.Available)
            }
        }
    }

    private fun getTeamsList(){
        val service = ContentService()
        val getTeamsUseCase = GetTeamListUseCase(service, selectedLeague)
        getTeamsUseCase.invoke(viewModelScope, null) {
            it.onFailure { updateLoadingState(LoadingState.OnError) }
            it.onSuccess { list ->
                updateLoadingState(LoadingState.OnSuccess)
                updateTeamState(TeamState.Available)
                updateTeamListInformation(list = list.teams)
            }
        }
    }

    private fun updateGeneralInformation(list: ArrayList<LeagueInfo>){
        mutableLeagueList.postValue(list)
        val category = mutableListOf<String>()
        for(elem in list){
            if(!category.any{ it == elem.strSport}){
                category.add(elem.strSport.orEmpty())
            }
        }
        mutableCategoryList.postValue(ArrayList(category))
    }

    private fun updateTeamListInformation(list: ArrayList<TeamInfo>){
        mutableTeamList.postValue(list)
    }

    private fun updateLeagueByCategoryInformation(){
        val availableLeagues = mutableListOf<String>()
        mutableLeagueList.value.notNull {
            for(elem in this){
                if(elem.strSport == selectedCategory){
                    availableLeagues.add(elem.strLeague.orEmpty())
                }
            }
        }
        mutableLeagueOnCategoryList.postValue(ArrayList(availableLeagues))
    }

    private fun updateTeamByCategoryInformation(){
        updateLoadingState(LoadingState.OnRetrieving)
        getTeamsList()
    }

    //region Update State
    private fun updateLoadingState(newState: LoadingState){
        loadingState.postValue(newState)
    }

    private fun updateCategoryState(newState: CategoryState){
        categoryState.postValue(newState)
    }

    private fun updateLeagueState(newState: LeagueState){
        leagueState.postValue(newState)
    }

    private fun updateTeamState(newState: TeamState){
        teamState.postValue(newState)
    }
    //endregion

    //region Commands
    fun onSelectedCategoryCommand(category: String){
        selectedCategory = category
        updateLeagueByCategoryInformation()
        updateCategoryState(CategoryState.Selected)
    }

    fun onSelectedLeagueCommand(league: String){
        selectedLeague = league
        updateTeamByCategoryInformation()
        updateLeagueState(LeagueState.Selected)
    }

    fun onSelectedTeam(name: String){
        var selectedTeamItem: TeamInfo? = null
        mutableTeamList.value.notNull {
            selectedTeamItem = this.firstOrNull { it.strTeam == name }
        }
        selectedTeamItem.notNull {
            selectedTeam.postValue(this)
        }
        updateTeamState(TeamState.Selected)
    }
    //endregion
}