package com.example.kotlinapidemo.ui.list

import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.architecture.base.ui.BaseActivity
import com.example.kotlinapidemo.LoadMoreInterface
import com.example.kotlinapidemo.adapter.ListFooterAdapter
import com.example.kotlinapidemo.adapter.ListHeaderAdapter
import com.example.kotlinapidemo.beans.TestDataBean
import com.example.kotlinapidemo.databinding.ActivityConcatListBinding
import com.example.kotlinapidemo.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConcatListActivity : BaseActivity<ActivityConcatListBinding, MainViewModel>() {

    private val concatAdapter by lazy {
        ConcatAdapter()
    }

    private val headerAdapter by lazy {
        ListHeaderAdapter()
    }

    private val footerAdapter by lazy {
        ListFooterAdapter().apply {
            loadMoreInterface = object : LoadMoreInterface {
                override fun loadMore() {
                    lifecycleScope.launch {
                        delay(1500)
                        concatAdapter.removeAdapter(this@apply)
                        dataAdapter.addDataList(
                            arrayListOf(
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean(),
                                TestDataBean()
                            )
                        )
                    }
                }
            }
        }
    }

    private val dataAdapter by lazy {
        ListTestDataAdapter()
    }

    override fun initEvent() {

        binding.listConcat.run {
            layoutManager = LinearLayoutManager(this@ConcatListActivity)
            adapter = concatAdapter
        }

        lifecycleScope.launch {
            delay(1500)
            concatAdapter.addAdapter(headerAdapter)
            dataAdapter.setDataList(
                arrayListOf(
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean(),
                    TestDataBean()
                )
            )
            concatAdapter.addAdapter(dataAdapter)
            concatAdapter.addAdapter(footerAdapter)
        }

    }
}