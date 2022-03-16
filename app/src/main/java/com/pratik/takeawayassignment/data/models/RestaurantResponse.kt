package com.pratik.takeawayassignment.data.models

class RestaurantResponse(val restaurants : List<RestaurantItem>){
    class RestaurantItem(val name: String, val status: String, val sortingValues: Map<String, Double>)
}