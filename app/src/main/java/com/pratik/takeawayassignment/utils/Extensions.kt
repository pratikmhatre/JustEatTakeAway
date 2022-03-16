package com.pratik.takeawayassignment.utils

import com.pratik.takeawayassignment.data.models.Restaurant
import com.pratik.takeawayassignment.data.models.RestaurantResponse


infix fun List<RestaurantResponse.RestaurantItem>.getRestaurantModelList(sortParam: String): List<Restaurant> {
    return this.map {
        Restaurant(
            name = it.name,
            openStatus = it.status,
            selectedSort = it.getSortClass(sortParam),
            showSortIndicator = sortParam != SortingParameters.None
        )
    }
}

private fun RestaurantResponse.RestaurantItem.getSortClass(sortParam: String): String {
    val values = this.sortingValues
    return "$sortParam : ${values[sortParam]}"
}