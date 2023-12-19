package com.konkuk.sadelivery.presentation.main.framgent

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.sadelivery.R
import com.konkuk.sadelivery.databinding.FragmentMyOrdersBinding
import com.konkuk.sadelivery.presentation.base.BaseFragment
import com.konkuk.sadelivery.presentation.main.MainViewModel
import com.konkuk.sadelivery.presentation.main.adapter.OrdersAdapter

class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding>() {
    override val TAG: String
        get() = "MyOrders"
    override val layoutRes: Int
        get() = R.layout.fragment_my_orders
    private val viewModel: MainViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.vm = viewModel
        val adapter = OrdersAdapter(object : OrdersAdapter.OrderSaveBtnListener {
            override fun onItemClicked(orderId: Long, rating: Int, content: String) {
                viewModel.postReview(orderId, 1, rating, content)
            }
        })

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        viewModel.requestOrderHistories()
    }
}