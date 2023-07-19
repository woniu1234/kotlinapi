package com.example.kotlinapidemo.ui

import android.content.Intent
import com.architecture.base.ui.BaseActivity
import com.example.kotlinapidemo.base.StateData
import com.example.kotlinapidemo.databinding.ActivityMainBinding
import com.example.kotlinapidemo.ui.list.ConcatListActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initEvent() {
        binding.tvText.setOnClickListener {
            viewModel.testData()
        }
        binding.tvRecyclerview.setOnClickListener {
            startActivity(Intent(this, ConcatListActivity::class.java))
        }
        viewModel.data.observe(this) {
            when (it.status) {
                StateData.DataStatus.SUCCESS -> {

                }

                StateData.DataStatus.ERROR -> {

                }

                else -> {}
            }
        }
    }

}