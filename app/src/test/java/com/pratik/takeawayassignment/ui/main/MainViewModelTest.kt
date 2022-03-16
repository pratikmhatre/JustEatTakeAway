package com.pratik.takeawayassignment.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.pratik.takeawayassignment.data.models.Restaurant
import com.pratik.takeawayassignment.utils.MainCoroutineRule
import com.pratik.takeawayassignment.utils.SortingParameters
import com.pratik.takeawayassignment.utils.getOrAwaitValue
import com.pratik.takeawayassignment.utils.getRestaurantModelList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainViewModel: MainViewModel

    private val mainRepository = MainRepositoryDummy()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(mainRepository, mainCoroutineRule.testDispatcherProvider)
    }

    @Test
    fun `searchRestaurantByName should give all restaurants having  given query in their name`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSearchResults = listOf(
                mainRepository.orderAheadRestaurant2,
                mainRepository.closedRestaurant1,
            ) getRestaurantModelList(SortingParameters.None)

            val query = "Burger"
            mainViewModel.searchRestaurantByName(query)

            val searchResults = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue {}
            assertThat(searchResults).isEqualTo(actualSearchResults)
        }

    @Test
    fun `on searching restaurants applied sort should change to None`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val query = "Burger"
            mainViewModel.searchRestaurantByName(query)

            val selectedSortParam = mainViewModel.selectedSortParameterLiveData.getOrAwaitValue {}
            assertThat(selectedSortParam).isEqualTo(SortingParameters.None)
        }


    @Test
    fun `selected sort parameter should be saved in local storage`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val sortParam = SortingParameters.MinCost
            mainViewModel.sortRestaurantsByParameter(sortParam)
            mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(mainRepository.getSavedSortParam()).isEqualTo(sortParam)
        }

    @Test
    fun `Sort by BestMatch should show the list in descending order of Best Match value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList: List<Restaurant> = listOf(
                mainRepository.openRestaurant4,
                mainRepository.openRestaurant5,
                mainRepository.orderAheadRestaurant2,
                mainRepository.orderAheadRestaurant3,
                mainRepository.closedRestaurant6,
                mainRepository.closedRestaurant1,
            ) getRestaurantModelList(SortingParameters.BestMatch)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.BestMatch)

            val sortedList: List<Restaurant> =
                mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }

            assertThat(sortedList).isEqualTo(actualSortedList)
        }


    @Test
    fun `Sort by Newest should show the list in descending order of Newest value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList = listOf(
                mainRepository.openRestaurant4,
                mainRepository.openRestaurant5,
                mainRepository.orderAheadRestaurant2,
                mainRepository.orderAheadRestaurant3,
                mainRepository.closedRestaurant6,
                mainRepository.closedRestaurant1
            ) getRestaurantModelList(SortingParameters.Newest)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.Newest)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Rating should show the list in descending order of Average Rating value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList = listOf(
                mainRepository.openRestaurant4,
                mainRepository.openRestaurant5,
                mainRepository.orderAheadRestaurant2,
                mainRepository.orderAheadRestaurant3,
                mainRepository.closedRestaurant6,
                mainRepository.closedRestaurant1
            ) getRestaurantModelList(SortingParameters.RatingAverage)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.RatingAverage)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Distance should show the list in ascending order of Distance value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList = listOf(
                mainRepository.openRestaurant5,
                mainRepository.openRestaurant4,
                mainRepository.orderAheadRestaurant3,
                mainRepository.orderAheadRestaurant2,
                mainRepository.closedRestaurant1,
                mainRepository.closedRestaurant6,
            ) getRestaurantModelList(SortingParameters.Distance)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.Distance)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Popularity should show the list in descending order of Popularity value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList = listOf(
                mainRepository.openRestaurant4,
                mainRepository.openRestaurant5,
                mainRepository.orderAheadRestaurant2,
                mainRepository.orderAheadRestaurant3,
                mainRepository.closedRestaurant6,
                mainRepository.closedRestaurant1
            ) getRestaurantModelList(SortingParameters.Popularity)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.Popularity)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Average Product Price should show the list in ascending order of Average Product Price value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            val actualSortedList = listOf(
                mainRepository.openRestaurant5,
                mainRepository.openRestaurant4,
                mainRepository.orderAheadRestaurant3,
                mainRepository.orderAheadRestaurant2,
                mainRepository.closedRestaurant1,
                mainRepository.closedRestaurant6,
            ) getRestaurantModelList(SortingParameters.AverageProductPrice)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.AverageProductPrice)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Delivery Cost should show the list in ascending order of Delivery Cost value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            val actualSortedList = listOf(
                mainRepository.openRestaurant5,
                mainRepository.openRestaurant4,
                mainRepository.orderAheadRestaurant3,
                mainRepository.orderAheadRestaurant2,
                mainRepository.closedRestaurant1,
                mainRepository.closedRestaurant6,
            ) getRestaurantModelList(SortingParameters.DeliveryCosts)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.DeliveryCosts)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort by Minimum Cost should show the list in ascending order of Minimum Cost value`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            val actualSortedList = listOf(
                mainRepository.openRestaurant5,
                mainRepository.openRestaurant4,
                mainRepository.orderAheadRestaurant3,
                mainRepository.orderAheadRestaurant2,
                mainRepository.closedRestaurant1,
                mainRepository.closedRestaurant6,
            ) getRestaurantModelList(SortingParameters.MinCost)

            mainViewModel.sortRestaurantsByParameter(SortingParameters.MinCost)
            val sortedList = mainViewModel.restaurantResponseListLiveData.getOrAwaitValue { }
            assertThat(sortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `Sort By Open status should show the list in order of open, order ahead, closed`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortedList = listOf(
                mainRepository.openRestaurant4,
                mainRepository.openRestaurant5,
                mainRepository.orderAheadRestaurant2,
                mainRepository.orderAheadRestaurant3,
                mainRepository.closedRestaurant1,
                mainRepository.closedRestaurant6,
            ) getRestaurantModelList(SortingParameters.None)

            val list: List<Restaurant> = mainRepository.getRestaurantList() getRestaurantModelList(SortingParameters.None)

            val viewModelSortedList = mainViewModel.sortRestaurantsByOpenState(list)

            assertThat(viewModelSortedList).isEqualTo(actualSortedList)
        }

    @Test
    fun `onShowSortOptionsClicked gives all available sorting values of first list item`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            val actualSortOptions = listOf(
                SortingParameters.None,
                "bestMatch",
                "newest",
                "ratingAverage",
                "distance",
                "popularity",
                "averageProductPrice",
                "deliveryCosts",
                "minCost"
            )
            mainViewModel.onShowSortOptionsClicked()
            val sortValuesResult =
                mainViewModel.availableSortOptionsListLiveData.getOrAwaitValue { }
            assertThat(sortValuesResult.peekContent()).isEqualTo(actualSortOptions)
        }
}