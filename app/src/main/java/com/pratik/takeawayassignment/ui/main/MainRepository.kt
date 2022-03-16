package com.pratik.takeawayassignment.ui.main

import com.pratik.takeawayassignment.data.models.RestaurantResponse

interface MainRepository {
    fun getRestaurantList(): List<RestaurantResponse.RestaurantItem>
    fun saveSortParam(param: String)
    fun getSavedSortParam(): String?
}