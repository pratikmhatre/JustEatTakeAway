
# Technical Assignment

This project is created for technical assignment as a part of the interview process. **Kotlin** is used as programming language with Model-View-ViewModel(MVVM) architecture.



## Third Party Libraries Used

- Dagger Hilt for dependency injection
- Kotlin Coroutines for multi threading
- Gson library for serialization
- RxBinding for reactive view events
- Google Truth for unit testing




### Features

- The restaurant list is parsed from a json file saved inside the app
- Initially the restaurant list sorted by open status is displayed in the activity.
- User can search restaurants by selecting search button in activity
- User can sort the list based on various sorting parameters such as Rating, Distance, Minimum Cost, Distance etc.
- The list is constantly sorted based on the open status where "open" restaurants will come first followed by "order ahead" and then by "closed" restaurants.
- The activity shows currently selected sorting parameter and the list of restaurants.
- Each list item always shows restaurant name and open status. And when a sorting parameter other than "None" is selected each item also shows the selected sort parameter along with corresponding value.
- As Viewmodel pattern is used the app retains the state over configuration changes.
- On closing the application and restarting it, the list sorted by the previously selected sort parameter is displayed.


## Running Tests

JUnit is used for unit testing along with Google Truth library. Kotlin Coroutine test dispatcher is used to run the multi threaded functions.

**Search Functionality Tests**
```
1. `searchRestaurantByName should give all restaurants having  given query in their name`
2. `on searching restaurants applied sort should change to None`
```

**Sort Functionality Tests**
```
1 `selected sort parameter should be saved in local storage`
2 `Sort by BestMatch should show the list in descending order of Best Match value`
3 `Sort by Newest should show the list in descending order of Newest value`
4 `Sort by Rating should show the list in descending order of Average Rating value`
5 `Sort by Distance should show the list in ascending order of Distance value`
6. `Sort by Popularity should show the list in descending order of Popularity value`
7. `Sort by Average Product Price should show the list in ascending order of Average Product Price value`
8. `Sort by Delivery Cost should show the list in ascending order of Delivery Cost value`
9. `Sort by Minimum Cost should show the list in ascending order of Minimum Cost value`

```
**Sort By Open Status Test**
```
`Sort By Open status should show the list in order of open, order ahead, closed`
```

**Get Sort Options Tests**
```
`onShowSortOptionsClicked gives all available sorting values of first list item`
```

All above tests can be found inside the test source set in file **ui > main > MainViewModelTest.class**