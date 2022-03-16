package com.pratik.takeawayassignment.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.jakewharton.rxbinding2.widget.RxTextView
import com.pratik.takeawayassignment.R
import com.pratik.takeawayassignment.base.BaseActivity
import com.pratik.takeawayassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), View.OnClickListener {

    @Inject
    lateinit var restaurantsListAdapter: RestaurantsListAdapter

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        initUiElements()
        initObservers()
    }

    private fun initUiElements() {
        with(binding) {
            recyclerView.adapter = restaurantsListAdapter
            btnSearch.setOnClickListener(this@MainActivity)
            btnSort.setOnClickListener(this@MainActivity)
            frameSort.setOnClickListener(this@MainActivity)
            btnBack.setOnClickListener(this@MainActivity)
            btnClear.setOnClickListener(this@MainActivity)

            RxTextView.afterTextChangeEvents(edSearch)
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .skip(1)
                .subscribe {
                    if (binding.frameSearch.visibility == View.VISIBLE) {
                        viewModel.searchRestaurantByName(edSearch.text.toString())
                    }
                }
        }

    }

    private fun initObservers() {
        viewModel.restaurantResponseListLiveData.observe(this) { list ->
            restaurantsListAdapter.restaurantsList = list
        }
        viewModel.availableSortOptionsListLiveData.observe(this) {
            it.getContentIfNotHanlded()?.let { list ->
                showSortOptionsDialog(list)
            }
        }
        viewModel.selectedSortParameterLiveData.observe(this) { param ->
            onSortOptionSelected(param)
        }

        viewModel.searchStateLiveData.observe(this) { showSearch ->
            toggleSearchFrame(showSearch)
            if (!showSearch) {
                clearSearchQuery()
            }
        }
    }

    private fun showSortOptionsDialog(list: List<String>) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(getString(R.string.sort_restaurants))
            setItems(list.toTypedArray()) { _, p1 ->
                val selectedSort = list[p1]
                viewModel.sortRestaurantsByParameter(selectedSort)
                viewModel.changeSearchState(false)
            }
        }
        alertDialog.show()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_search -> viewModel.changeSearchState(true)
            R.id.btn_clear -> clearSearchQuery()
            R.id.btn_sort, R.id.frame_sort -> viewModel.onShowSortOptionsClicked()
            R.id.btn_back -> {
                viewModel.changeSearchState(false)
                viewModel.fetchInitialList()
            }
        }
    }

    private fun clearSearchQuery() {
        binding.edSearch.setText("")
    }

    private fun onSortOptionSelected(param: String) {
        val sortText = String.format(getString(R.string.sorted_by), param)
        binding.tvSortedBy.text = sortText
    }

    private fun toggleSearchFrame(shouldShow: Boolean) {
        binding.edSearch.requestFocus()
        toggleSoftInputKeyboard(shouldShow)
        binding.frameSearch.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

}