package com.mobisy.claims.ui

import android.os.Bundle
import com.mobisy.claims.BR
import com.mobisy.claims.R
import com.mobisy.claims.databinding.ActivityDynamicFormBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DynamicFormActivity : BaseActivity<ActivityDynamicFormBinding, DynamicFormViewModel>() {
    private lateinit var dynamicFormBinding: ActivityDynamicFormBinding
    private lateinit var dynamicFormViewModel: DynamicFormViewModel

    override fun onCreate(
        instance: Bundle?,
        binding: ActivityDynamicFormBinding,
        viewModel: DynamicFormViewModel
    ) {
        dynamicFormBinding = binding
        dynamicFormViewModel = viewModel

        generateView()
    }

    private fun generateView() {


    }

    override fun setBindVariable(): Int = BR.viewModel

    override fun setViewModel(): Class<DynamicFormViewModel> = DynamicFormViewModel::class.java

    override fun setLayoutResId(): Int = R.layout.activity_dynamic_form
}