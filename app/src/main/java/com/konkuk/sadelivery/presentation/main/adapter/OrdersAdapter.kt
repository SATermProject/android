package com.konkuk.sadelivery.presentation.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.sadelivery.databinding.ItemOrderBinding
import com.konkuk.sadelivery.entity.OrderHistory

class OrdersAdapter(
    val itemClickedListener: OrderSaveBtnListener
) : RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {

    var orders: List<OrderHistory> = emptyList()

    interface OrderSaveBtnListener {
        fun onItemClicked(orderId: Long, rating: Int, content: String)
    }

    inner class MyViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderHistory) {
            Log.d("DDD","order binding: $order")
            binding.order = order
            order.review?.let {
                binding.ratingView.rating = it.rating.toFloat()
                binding.content.setText(it.content)
            }
            binding.saveBtn.setOnClickListener {
                itemClickedListener.onItemClicked(
                    order.id,
                    binding.ratingView.rating.toInt(),
                    binding.content.text.toString()
                )
            }
            binding.executePendingBindings()
        }

        fun cleanView(){
            binding.ratingView.rating = 0f
            binding.content.setText("")
        }
    }

    override fun onViewRecycled(holder: MyViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanView()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(orders[position])
    }

}