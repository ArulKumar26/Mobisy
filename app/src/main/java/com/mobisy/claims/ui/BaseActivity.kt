package com.mobisy.claims.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobisy.claims.utils.NetworkUtils


abstract class BaseActivity<D : ViewDataBinding, VM : ViewModel> : AppCompatActivity(),
    IBaseView {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<D>(this, setLayoutResId())
        val viewModel = ViewModelProvider(this)[setViewModel()]
        binding.setVariable(setBindVariable(), viewModel)
        onCreate(savedInstanceState, binding, viewModel)
        binding.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    override fun isNetworkConnected(): Boolean = NetworkUtils.isNetWorkAvailable(this)

    protected abstract fun onCreate(instance: Bundle?, binding: D, viewModel: VM)

    protected abstract fun setBindVariable(): Int

    protected abstract fun setViewModel(): Class<VM>

    @LayoutRes
    protected abstract fun setLayoutResId(): Int
}