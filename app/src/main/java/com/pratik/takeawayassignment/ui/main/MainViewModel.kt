package com.pratik.takeawayassignment.ui.main

import androidx.lifecycle.*
import com.pratik.takeawayassignment.data.models.Restaurant
import com.pratik.takeawayassignment.utils.DispatcherProvider
import com.pratik.takeawayassignment.utils.Event
import com.pratik.takeawayassignment.utils.SortingParameters
import com.pratik.takeawayassignment.utils.getRestaurantModelList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val statusOpen = "open"
    private val statusOrderAhead = "order ahead"
    private val statusClosed = "closed"

    private val _restaurantListLiveData = MutableLiveData<List<Restaurant>>()
    val restaurantResponseListLiveData: LiveData<List<Restaurant>> = _restaurantListLiveData.map {
        sortRestaurantsByOpenState(it)
    }

    private val _availableSortOptionsListLiveData = MutableLiveData<Event<List<String>>>()
    val availableSortOptionsListLiveData: LiveData<Event<List<String>>> =
        _availableSortOptionsListLiveData

    private val _selectedSortParameterLiveData = MutableLiveData<String>()
    val selectedSortParameterLiveData: LiveData<String> = _selectedSortParameterLiveData

    private val _searchStateLiveData = MutableLiveData<Boolean>()
    val searchStateLiveData: LiveData<Boolean> = _searchStateLiveData

    init {
        fetchInitialList()
    }

    fun fetchInitialList() {
        sortRestaurantsByParameter(mainRepository.getSavedSortParam() ?: SortingParameters.None)
    }

    fun searchRestaurantByName(query: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            val filteredList = mainRepository.getRestaurantList().filter {
                it.name.lowercase().contains(query.lowercase())
            }
            val restaurantList = filteredList.getRestaurantModelList(SortingParameters.None)
            _restaurantListLiveData.postValue(restaurantList)
            _selectedSortParameterLiveData.postValue(SortingParameters.None)
        }
    }

    fun sortRestaurantsByParameter(parameter: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            //save sort param to shared prefs
            mainRepository.saveSortParam(parameter)

            //show selected sort param in ui
            _selectedSortParameterLiveData.postValue(parameter)

            val restaurantsList = mainRepository.getRestaurantList()
            val sortedList = when (parameter) {
                SortingParameters.MinCost, SortingParameters.DeliveryCosts, SortingParameters.Distance, SortingParameters.AverageProductPrice -> restaurantsList.sortedBy {
                    it.sortingValues[parameter]
                }
                SortingParameters.BestMatch, SortingParameters.Newest, SortingParameters.RatingAverage, SortingParameters.Popularity -> restaurantsList.sortedByDescending {
                    it.sortingValues[parameter]
                }
                else -> restaurantsList
            }

            //set selected sort indicator
            val mappedList = sortedList.getRestaurantModelList(parameter)

            _restaurantListLiveData.postValue(
                mappedList
            )

        }
    }

    fun sortRestaurantsByOpenState(restaurantResponseList: List<Restaurant>): List<Restaurant> {
        val sortedList = ArrayList<Restaurant>()
        val grouped = restaurantResponseList.groupBy {
            it.openStatus
        }

        //add open restaurants first
        grouped[statusOpen]?.run {
            sortedList.addAll(this)
        }

        //add order ahead
        grouped[statusOrderAhead]?.run {
            sortedList.addAll(this)
        }

        //add closed restaurants at last
        grouped[statusClosed]?.run {
            sortedList.addAll(this)
        }
        return sortedList
    }

    fun onShowSortOptionsClicked() {
        viewModelScope.launch(dispatcherProvider.io()) {
            val sortOptionsList = ArrayList<String>()
            val restaurantsList = mainRepository.getRestaurantList()

            if (restaurantsList.isNotEmpty()) {
                val firstItem = restaurantsList.first()
                val sortParams = firstItem.sortingValues.keys.toList()

                sortOptionsList.addAll(sortParams)

                //add None option in sort params to remove all applied sorting
                sortOptionsList.add(0, SortingParameters.None)

                _availableSortOptionsListLiveData.postValue(Event(sortOptionsList))
            }
        }
    }

    fun changeSearchState(shouldShow: Boolean) {
        _searchStateLiveData.postValue(shouldShow)
    }
}