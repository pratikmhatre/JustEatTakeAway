package com.pratik.takeawayassignment.ui.main

import com.pratik.takeawayassignment.data.DataManager
import com.pratik.takeawayassignment.data.models.RestaurantResponse
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val restaurantsList: List<RestaurantResponse.RestaurantItem>, private val dataManager: DataManager
) : MainRepository {
    override fun getRestaurantList() = restaurantsList

    override fun saveSortParam(param: String) {
        dataManager.saveSortParam(param)
    }

    override fun getSavedSortParam() = dataManager.getSavedSortParam()
}