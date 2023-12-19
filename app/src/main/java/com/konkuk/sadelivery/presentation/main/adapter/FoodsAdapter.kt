package com.konkuk.sadelivery.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.sadelivery.databinding.ItemFoodBinding
import com.konkuk.sadelivery.entity.Food


class FoodsAdapter(
    val itemClickedListener: OnFoodClickListener
) : RecyclerView.Adapter<FoodsAdapter.MyViewHolder>() {

    var foods: List<Food> = emptyList()

    var selectedFoodBinding: ItemFoodBinding? = null

    interface OnFoodClickListener {
        fun onItemClicked(food: Food)
    }

    inner class MyViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.food = food
            binding.root.setOnClickListener {
                val isSelected = !binding.root.isSelected
                binding.root.isSelected = isSelected
                if (isSelected) {
                    selectedFoodBinding = binding
                    itemClickedListener.onItemClicked(food)
                } else selectedFoodBinding = null
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    fun deselect() {
        selectedFoodBinding?.let {
            it.root.isSelected = false
        }
    }

}