package com.konkuk.sadelivery.presentation.main

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.konkuk.sadelivery.R
import com.konkuk.sadelivery.databinding.ActivityMainBinding
import com.konkuk.sadelivery.presentation.base.BaseActivity
import com.konkuk.sadelivery.presentation.main.framgent.DetailFragment
import com.konkuk.sadelivery.presentation.main.framgent.HomeFragment
import com.konkuk.sadelivery.presentation.main.framgent.MyOrdersFragment
import com.konkuk.sadelivery.utils.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val TAG: String = "MainActivity"
    override val layoutRes: Int = R.layout.activity_main
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(MainRepository())
        )[MainViewModel::class.java]
    }

    private val homeFragment by lazy {
        supportFragmentManager.findFragmentByTag(HomeFragment::class.java.name) ?: HomeFragment()
    }
    private val detailFragment by lazy {
        supportFragmentManager.findFragmentByTag(DetailFragment::class.java.name)
            ?: DetailFragment()
    }
    private val ordersFragment by lazy {
        supportFragmentManager.findFragmentByTag(MyOrdersFragment::class.java.name)
            ?: MyOrdersFragment()
    }

    private var backButtonPressedOnce = false

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (supportFragmentManager.fragments.first { it.isVisible }) {
                is HomeFragment -> {
                    if (backButtonPressedOnce) finish()
                    else {
                        backButtonPressedOnce = true
                        showToast("한 번 더 누르면 종료됩니다")
                        lifecycleScope.launch {
                            delay(2000)
                            backButtonPressedOnce = false
                        }
                    }
                }
                else -> {
                    viewModel.goto(MainPage.HOME)
                }
            }
        }
    }

    override fun afterViewCreated() {
        onBackPressedDispatcher.addCallback(this, callback)
        collectPage()
    }


    private fun getFragment(page: MainPage): Fragment {
        return when (page) {
            MainPage.HOME -> homeFragment
            MainPage.Detail -> detailFragment
            MainPage.MYORDER -> ordersFragment
        }
    }

    private fun collectPage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var prevPage = viewModel.pageFlow.value
                viewModel.pageFlow.collect { page ->
                    val preFragment = getFragment(prevPage)
                    val fragment = getFragment(page)
                    supportFragmentManager.commit {
                        if (preFragment != fragment) hide(preFragment)
                        if (fragment.isAdded) show(fragment)
                        else add(R.id.frameLayout, fragment, fragment.javaClass.name)
                    }
                    prevPage = page
                }
            }
        }
    }
}