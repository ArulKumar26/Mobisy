package com.mobisy.claims.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobisy.claims.BR
import com.mobisy.claims.R
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.network.Status
import com.mobisy.claims.databinding.ActivityClaimItemBinding
import com.mobisy.claims.databinding.LayoutClaimItemBinding
import com.mobisy.claims.extensions.gone
import com.mobisy.claims.extensions.show
import com.mobisy.claims.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClaimItemActivity : BaseActivity<ActivityClaimItemBinding, MainViewModel>(),
    RecyclerViewListener {
    private lateinit var claimBinding: ActivityClaimItemBinding
    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(
        instance: Bundle?,
        binding: ActivityClaimItemBinding,
        viewModel: MainViewModel
    ) {
        claimBinding = binding
        mainViewModel = viewModel
        setAdapter()
        setupObserver()
        mainViewModel.fetchClaims()
    }

    private fun setupObserver() {
        mainViewModel.getClaims().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    claimBinding.progressBar.gone()
                    claimBinding.rvClaims.show()
                    it.data?.let { claims -> renderList(claims) }
                }
                Status.LOADING -> {
                    claimBinding.progressBar.show()
                    claimBinding.rvClaims.gone()
                }
                Status.ERROR -> {
                    claimBinding.progressBar.gone()
                    showMessage(it.message)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(claims: List<ResultClaimData>) {
        adapter.addItems(claims)
        adapter.notifyDataSetChanged()
    }

    override fun setBindVariable(): Int = BR.viewModel

    override fun setViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setLayoutResId(): Int = R.layout.activity_claim_item

    override fun onBindData(dataBinding: Any, model: Any, position: Int) {
        model as ResultClaimData
        dataBinding as LayoutClaimItemBinding
        dataBinding.claims = model
    }

    override fun onItemClick(model: Any, position: Int) {
    }

    private fun setAdapter() {
        adapter = RecyclerViewAdapter(R.layout.layout_claim_item, this)
        claimBinding.rvClaims.addItemDecoration(
            DividerItemDecoration(
                claimBinding.rvClaims.context,
                (claimBinding.rvClaims.layoutManager as LinearLayoutManager).orientation
            )
        )
        claimBinding.rvClaims.adapter = adapter
    }
}