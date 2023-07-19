package com.architecture.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<V : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
            .statusBarDarkFont(true)
            .init()
        try {
            binding = supportBinding()
            setContentView(binding.root)
            viewModel = supportViewModel()
            initEvent()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected abstract fun initEvent()

    protected fun supportViewModel(): VM {
        val type = this.javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments = type.actualTypeArguments[1] as Class<VM>
        return ViewModelProvider(
            (this as ViewModelStoreOwner),
            (ViewModelProvider.NewInstanceFactory() as ViewModelProvider.Factory)
        )[actualTypeArguments]
    }

    protected fun supportBinding(): V {
        val type = this.javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments = type.actualTypeArguments[0] as Class<V>
        return actualTypeArguments.getMethod("inflate", LayoutInflater::class.java)
            .invoke(null, LayoutInflater.from(this)) as V
    }

}