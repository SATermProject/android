package com.konkuk.sadelivery.presentation.main.framgent

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.sadelivery.R
import com.konkuk.sadelivery.databinding.FragmentHomeBinding
import com.konkuk.sadelivery.entity.Restaurant
import com.konkuk.sadelivery.presentation.base.BaseFragment
import com.konkuk.sadelivery.presentation.main.MainPage
import com.konkuk.sadelivery.presentation.main.MainViewModel
import com.konkuk.sadelivery.presentation.main.SortType
import com.konkuk.sadelivery.presentation.main.adapter.OnRestaurantClickListener
import com.konkuk.sadelivery.presentation.main.adapter.RestaurantsAdapter
import com.konkuk.sadelivery.presentation.main.adapter.SearchAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val TAG: String = "HomeFragment"
    override val layoutRes: Int = R.layout.fragment_home
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: RestaurantsAdapter

    override fun afterViewCreated() {
        binding.vm = viewModel

        adapter = RestaurantsAdapter(object : OnRestaurantClickListener {
            override fun onClicked(restaurant: Restaurant) {
                viewModel.onRestaurantClicked(restaurant)
            }
        })
        binding.restaurantsLayout.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.restaurantsLayout.recyclerView.adapter = adapter

        binding.searchBar.setOnMenuItemClickListener {
            viewModel.requestOrderHistories()
            viewModel.goto(MainPage.MYORDER)
            true
        }

        val searchAdapter = SearchAdapter(object : OnRestaurantClickListener {
            override fun onClicked(restaurant: Restaurant) {
                viewModel.onRestaurantSearched(restaurant)
            }
        })
        binding.searchRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.searchRecyclerView.adapter = searchAdapter

        binding.searchView.editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.searchKeyword(binding.searchView.text.toString())
                    Log.d(TAG, "searched:: ${binding.searchView.text}")
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        )

        showDropDownMenu()
        viewModel.requestRestaurantList(SortType.RATING, 1)
    }

    private fun showDropDownMenu() {
        val items = listOf("나의 별점 순","나의 검색어 순","나의 주문 기록 순","나의 리뷰 키워드 순")
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.item_text, items)
        (binding.restaurantsLayout.filtering.editText as? AutoCompleteTextView)?.setAdapter(menuAdapter)
        (binding.restaurantsLayout.filtering.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, pos, id ->
            when(pos){
                0-> viewModel.requestRestaurantList(SortType.RATING, 1)
                1-> viewModel.requestRestaurantList(SortType.SEARCH, 1)
                2-> viewModel.requestRestaurantList(SortType.ORDER, 1)
                3-> viewModel.requestRestaurantList(SortType.REVIEW, 1)
            }
        }
    }

}