package com.konkuk.sadelivery.presentation.main.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.sadelivery.databinding.ItemTextBinding
import com.konkuk.sadelivery.entity.Restaurant


class SearchAdapter(
    val itemClickedListener: OnRestaurantClickListener
) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    var restaurants: List<Restaurant> = emptyList()


    inner class MyViewHolder(private val binding: ItemTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.textView.text = restaurant.name
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
            ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = restaurants.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

}