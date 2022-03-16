package com.pratik.takeawayassignment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pratik.takeawayassignment.data.models.Restaurant
import com.pratik.takeawayassignment.databinding.ItemRestaurantBinding
import javax.inject.Inject

class RestaurantsListAdapter @Inject constructor() :
    RecyclerView.Adapter<RestaurantsListAdapter.RestaurantHolder>() {

    var restaurantsList: List<Restaurant>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val itemDiffCallbacks = object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant) =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, itemDiffCallbacks)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val rootBinding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantHolder(rootBinding)
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        holder.bindItem(restaurantsList[position])
    }

    override fun getItemCount() = restaurantsList.size

    inner class RestaurantHolder(private val itemRestaurantBinding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(itemRestaurantBinding.root) {
        fun bindItem(restaurant: Restaurant) {
            itemRestaurantBinding.apply {
                tvName.text = restaurant.name
                tvStatus.text = restaurant.openStatus

                if (restaurant.showSortIndicator) {
                    tvSortValue.text = restaurant.selectedSort
                    tvSortValue.visibility = View.VISIBLE
                } else {
                    tvSortValue.visibility = View.GONE
                }
            }
        }
    }
}