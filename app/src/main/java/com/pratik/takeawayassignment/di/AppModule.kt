package com.pratik.takeawayassignment.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.pratik.takeawayassignment.data.DataManager
import com.pratik.takeawayassignment.data.DataManagerImpl
import com.pratik.takeawayassignment.data.models.RestaurantResponse
import com.pratik.takeawayassignment.ui.main.MainRepository
import com.pratik.takeawayassignment.ui.main.MainRepositoryImpl
import com.pratik.takeawayassignment.utils.DefaultDispatcherProvider
import com.pratik.takeawayassignment.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRestaurantList(@ApplicationContext context: Context): List<RestaurantResponse.RestaurantItem> {
        val string = context.assets.open("restaurants.json").bufferedReader().use {
            it.readText()
        }
        val restaurantData = Gson().fromJson(string, RestaurantResponse::class.java)
        return restaurantData.restaurants
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository {
        return mainRepositoryImpl
    }

    @Singleton
    @Provides
    fun provideCorotuineDispatcherProvider(defaultProvider: DefaultDispatcherProvider): DispatcherProvider {
        return defaultProvider
    }
}