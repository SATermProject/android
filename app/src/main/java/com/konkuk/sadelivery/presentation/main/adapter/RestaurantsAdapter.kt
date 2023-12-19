package com.konkuk.sadelivery.presentation.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.sadelivery.databinding.ItemRestaurantBinding
import com.konkuk.sadelivery.entity.Restaurant


class RestaurantsAdapter(
    val itemClickedListener: OnRestaurantClickListener
) : RecyclerView.Adapter<RestaurantsAdapter.MyViewHolder>() {

    var restaurants: List<Restaurant> = emptyList()


    inner class MyViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.restaurant = restaurant
            binding.root.setOnClickListener {
                itemClickedListener.onClicked(restaurant)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

}