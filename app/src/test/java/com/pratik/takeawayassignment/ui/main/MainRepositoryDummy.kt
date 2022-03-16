package com.pratik.takeawayassignment.ui.main

import com.pratik.takeawayassignment.data.models.RestaurantResponse
import com.pratik.takeawayassignment.utils.SortingParameters

class MainRepositoryDummy : MainRepository {
    private var selectedSort: String? = null
    val closedRestaurant1 = generateRestaurantObject(
        name = "Burger King",
        openStatus = "closed",
        bestMatch = 100.0,
        newest = 50.0,
        ratingAverage = 3.0,
        popularity = 150.0,
        distance = 300.0,
        averageProductPrice = 500.0,
        deliveryCosts = 130.0,
        minCost = 1200.0
    )
    val orderAheadRestaurant2 = generateRestaurantObject(
        name = "Burger Express",
        openStatus = "order ahead",
        bestMatch = 130.0,
        newest = 80.0,
        ratingAverage = 3.3,
        distance = 600.0,
        popularity = 180.0,
        averageProductPrice = 800.0,
        deliveryCosts = 430.0,
        minCost = 1500.0,
    )
    val orderAheadRestaurant3 = generateRestaurantObject(
        name = "Tandoori Corner",
        openStatus = "order ahead",
        bestMatch = 110.0,
        newest = 60.0,
        ratingAverage = 3.1,
        distance = 400.0,
        popularity = 160.0,
        averageProductPrice = 600.0,
        deliveryCosts = 230.0,
        minCost = 1300.0,
    )

    val openRestaurant4 = generateRestaurantObject(
        name = "Sizzling Thai",
        openStatus = "open",
        bestMatch = 150.0,
        newest = 100.0,
        ratingAverage = 3.5,
        distance = 800.0,
        popularity = 200.0,
        averageProductPrice = 1000.0,
        deliveryCosts = 630.0,
        minCost = 1700.0,
    )
    val openRestaurant5 = generateRestaurantObject(
        name = "Pizza Express",
        openStatus = "open",
        bestMatch = 120.0,
        newest = 70.0,
        ratingAverage = 3.2,
        distance = 500.0,
        popularity = 170.0,
        averageProductPrice = 700.0,
        deliveryCosts = 330.0,
        minCost = 1400.0,
    )

    val closedRestaurant6 = generateRestaurantObject(
        name = "Best of Chinese",
        openStatus = "closed",
        bestMatch = 140.0,
        newest = 90.0,
        ratingAverage = 3.4,
        distance = 700.0,
        popularity = 190.0,
        averageProductPrice = 900.0,
        deliveryCosts = 530.0,
        minCost = 1600.0,
    )

    override fun getRestaurantList() =
        listOf(
            closedRestaurant1,
            orderAheadRestaurant2,
            orderAheadRestaurant3,
            openRestaurant4,
            openRestaurant5,
            closedRestaurant6
        )

    private fun generateRestaurantObject(
        name: String,
        openStatus: String,
        bestMatch: Double,
        newest: Double,
        ratingAverage: Double,
        distance: Double,
        popularity: Double,
        averageProductPrice: Double,
        deliveryCosts: Double,
        minCost: Double
    ): RestaurantResponse.RestaurantItem {
        val sortingValues = mapOf(
            SortingParameters.BestMatch to bestMatch,
            SortingParameters.Newest to newest,
            SortingParameters.RatingAverage to ratingAverage,
            SortingParameters.Distance to distance,
            SortingParameters.Popularity to popularity,
            SortingParameters.AverageProductPrice to averageProductPrice,
            SortingParameters.DeliveryCosts to deliveryCosts,
            SortingParameters.MinCost to minCost
        )
        return RestaurantResponse.RestaurantItem(name = name, status = openStatus, sortingValues)
    }

    override fun saveSortParam(param: String) {
        selectedSort = param
    }

    override fun getSavedSortParam() = selectedSort
}