package com.konkuk.sadelivery.presentation.main.framgent

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.sadelivery.R
import com.konkuk.sadelivery.databinding.FragmentDetailBinding
import com.konkuk.sadelivery.entity.Food
import com.konkuk.sadelivery.presentation.base.BaseFragment
import com.konkuk.sadelivery.presentation.main.adapter.FoodsAdapter
import com.konkuk.sadelivery.presentation.main.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override val TAG: String
        get() = "Detail"
    override val layoutRes: Int
        get() = R.layout.fragment_detail
    private val viewModel: MainViewModel by activityViewModels()

    override fun afterViewCreated() {
        binding.vm = viewModel
        val adapter = FoodsAdapter(object : FoodsAdapter.OnFoodClickListener {
            override fun onItemClicked(food: Food) {

            }
        })
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        binding.orderBtn.setOnClickListener {
            viewModel.postOrder(adapter.selectedFoodBinding?.food)
        }

        viewModel.postOrderResponse.observe(this){
            showToast("${it.name} 주문 완료")
        }
    }

}