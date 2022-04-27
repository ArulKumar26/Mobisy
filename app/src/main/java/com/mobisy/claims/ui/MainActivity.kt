package com.mobisy.claims.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.mobisy.claims.BR
import com.mobisy.claims.R
import com.mobisy.claims.data.model.*
import com.mobisy.claims.databinding.ActivityMainBinding
import com.mobisy.claims.extensions.launchActivity
import com.mobisy.claims.extensions.margin
import com.mobisy.claims.extensions.selected
import com.mobisy.claims.extensions.showMessage
import com.mobisy.claims.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var rootView: LinearLayout

    private lateinit var claimData: ArrayList<Claims>
    private lateinit var claims: Claims
    private lateinit var dependantData: List<ClaimTypeDetail>
    private var dependantViewIndex: Int = 0

    override fun onCreate(
        instance: Bundle?,
        binding: ActivityMainBinding,
        viewModel: MainViewModel
    ) {
        mainBinding = binding
        mainViewModel = viewModel

        generateViewsData()
    }

    override fun setBindVariable(): Int = BR.viewModel

    override fun setViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setLayoutResId(): Int = R.layout.activity_main

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_claim_items -> launchActivity<ClaimItemActivity> {}
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Get the data from json file
     */
    private fun generateViewsData() {
        claimData = FileUtils.generateData(applicationContext)
        if (claimData.isNullOrEmpty()) return
        val claimTypes = ArrayList<ClaimType>()
        claimData.forEach {
            it.claimtype?.let { claimType ->
                claimTypes.add(claimType)
            }
        }
        if (claimTypes.size > 0) createClaimsType(claimTypes)
    }

    /**
     * Load claim type in dropdown
     */
    @SuppressLint("InflateParams")
    private fun createClaimsType(claimData: ArrayList<ClaimType>) {
        if (claimData.isNullOrEmpty()) return

        // Create Layout for dynamic form
        rootView = LinearLayout(this)
        rootView.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        rootView.orientation = LinearLayout.VERTICAL

        //Create Claim type dropdown
        val spinner =
            layoutInflater.inflate(R.layout.widget_spinner, null) as Spinner?
        spinner?.tag = "claim_type"
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            claimData
        )
        spinner?.adapter = adapter
        mainBinding.llRoot.addView(spinner)
        spinner?.selected { _, _, position, _ ->
            rootView.removeAllViews()
            //Create view based on claim type
            createViews(position)
        }

        createClaimDate()
    }

    /**
     * Create claim date view
     */
    @SuppressLint("InflateParams")
    private fun createClaimDate() {
        val claimDate = layoutInflater.inflate(R.layout.widget_textview, null) as TextView?
        claimDate?.text = getString(R.string.claim_date)
        mainBinding.llRoot.addView(claimDate)

        val claimDateValue = layoutInflater.inflate(R.layout.widget_textview, null) as TextView?
        claimDateValue?.apply {
            text = DateTimeUtils.getTodayDate()
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.purple_500))
            setTypeface(claimDateValue.typeface, Typeface.BOLD)
            textSize = 15f
            mainBinding.llRoot.addView(this)
        }
    }

    /**
     * Create layout based on claim type
     */
    private fun createViews(position: Int) {
        claims = claimData[position]
        getDependentItem()
        claims.claimtypedetail.forEachIndexed { index, claimTypeDetail ->
            when (claimTypeDetail.claimfield?.type) {
                WIDGET_EDIT_TEXT_ALL_CAPS, WIDGET_EDIT_TEXT_NUMERIC, WIDGET_EDIT_TEXT_SINGLE_LINE -> createEditText(
                    claimTypeDetail
                )
                WIDGET_SPINNER -> if (claimTypeDetail.claimfield?.isdependant.equals("0")) createSpinner(
                    claimTypeDetail
                ) else dependantViewIndex = index
            }
        }

        createButton()
        if (rootView.parent != null) (rootView.parent as ViewGroup).removeView(rootView)
        mainBinding.llRoot.addView(rootView)
    }

    /**
     * Create drop down based on claim type
     */
    @SuppressLint("InflateParams")
    private fun createSpinner(claimTypeDetail: ClaimTypeDetail) {
        createTextView(claimTypeDetail)
        val claimField = claimTypeDetail.claimfield
        if (claimField?.claimfieldoption == null || claimField.claimfieldoption.isNullOrEmpty()) {
            return
        }
        val spinner = layoutInflater.inflate(R.layout.widget_spinner, null) as Spinner?
        spinner?.tag = claimField.id
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            claimField.claimfieldoption
        )
        spinner?.adapter = adapter
        rootView.addView(spinner)
        spinner?.selected { _, _, _, _ ->
            val selection = spinner.selectedItem as ClaimFieldOption
            dependantData.forEach { claimTypeDetail ->
                val dependant =
                    claimTypeDetail.claimfield?.claimfieldoption?.filter { claimFieldOption ->
                        claimFieldOption.belongsto.equals(
                            selection.id
                        )
                    }
                if (dependant?.size!! > 0) createDependantSpinner(claimTypeDetail, dependant)
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun createDependantSpinner(
        claimTypeDetail: ClaimTypeDetail,
        claimFieldOption: List<ClaimFieldOption>
    ) {
        createDependantTextView(claimTypeDetail)
        val claimField = claimTypeDetail.claimfield
        val dependantSpinner =
            layoutInflater.inflate(R.layout.widget_spinner, null) as Spinner?
        dependantSpinner?.tag = claimField?.id
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            claimFieldOption
        )
        dependantSpinner?.adapter = adapter
        val spinner =
            rootView.findViewWithTag(claimTypeDetail.claimfield?.id) as Spinner?
        if (spinner?.parent != null) {
            (spinner.parent as ViewGroup).removeView(spinner)
        }
        //Bcoz I added two views manually name claim date and claim date value
        rootView.addView(dependantSpinner, dependantViewIndex + 4)
    }

    /**
     * Create text based on claim type
     */
    @SuppressLint("InflateParams")
    private fun createTextView(claimTypeDetail: ClaimTypeDetail) {
        val textView =
            layoutInflater.inflate(R.layout.widget_textview, null) as TextView?
        textView?.tag = claimTypeDetail.claimfield?.name
        if (claimTypeDetail.claimfield?.required.equals("1")) {
            textView?.text = getString(R.string.mandatory, claimTypeDetail.claimfield?.label)
        } else {
            textView?.text = claimTypeDetail.claimfield?.label
        }
        rootView.addView(textView)
    }

    @SuppressLint("InflateParams")
    private fun createDependantTextView(claimTypeDetail: ClaimTypeDetail) {
        val textView =
            layoutInflater.inflate(R.layout.widget_textview, null) as TextView?
        textView?.tag = claimTypeDetail.claimfield?.name
        if (claimTypeDetail.claimfield?.required.equals("1")) {
            textView?.text = getString(R.string.mandatory, claimTypeDetail.claimfield?.label)
        } else {
            textView?.text = claimTypeDetail.claimfield?.label
        }
        val dependantTextView =
            rootView.findViewWithTag(claimTypeDetail.claimfield?.name) as TextView?
        if (dependantTextView?.parent != null) {
            (dependantTextView.parent as ViewGroup).removeView(dependantTextView)
        }
        //Bcoz I added two views manually name claim date and claim date value
        rootView.addView(textView, dependantViewIndex + 3)
    }

    /**
     * Create text field based on claim type
     */
    @SuppressLint("InflateParams")
    private fun createEditText(it: ClaimTypeDetail) {
        val editText =
            layoutInflater.inflate(R.layout.widget_edittext, null) as EditText?
        editText?.apply {
            margin(top = 15f)
            tag = it.claimfield?.id
            hint = it.claimfield?.label
            inputType = getInputType(it.claimfield?.type)
            isSingleLine = true
            if (it.claimfield?.type.equals(
                    WIDGET_EDIT_TEXT_ALL_CAPS,
                    ignoreCase = true
                )
            ) {
                filters =
                    arrayOf<InputFilter>(InputFilter.AllCaps())
            }
        }
        rootView.addView(editText)
    }

    /**
     * Create button for add claim item
     */
    @SuppressLint("InflateParams")
    private fun createButton() {
        val button = layoutInflater.inflate(R.layout.widget_button, null) as Button
        with(button) {
            margin(top = 25f)
            text = getString(R.string.add_claim)
            setOnClickListener {
                val resultData = getDataFromView()
                resultData?.let { mainViewModel.insertClaims(it) }
                showMessage("Claim Item saved successfully !")
            }
        }
        rootView.addView(button)
    }

    private fun getDataFromView(): ResultClaimData? {
        if (claims.claimtypedetail.isNullOrEmpty()) return null

        val claimTypeSpinner = mainBinding.llRoot.findViewWithTag("claim_type") as Spinner?
        val claimTypeSelectedItem = claimTypeSpinner?.selectedItem as ClaimType
        val resultClaimData = ResultClaimData()
        resultClaimData.claimDate = DateTimeUtils.getTodayDate()
        resultClaimData.claimTypeId = claimTypeSelectedItem.id
        resultClaimData.claimType = claimTypeSelectedItem.name

        claims.claimtypedetail.forEach { claimTypeDetail ->
            when (claimTypeDetail.claimfield?.type) {
                WIDGET_EDIT_TEXT_ALL_CAPS, WIDGET_EDIT_TEXT_NUMERIC, WIDGET_EDIT_TEXT_SINGLE_LINE -> {
                    val edit = rootView.findViewWithTag(claimTypeDetail.claimfield?.id) as EditText
                    val resultClaimTypeOption = ResultClaimTypeOption().apply {
                        enteredValueId = edit.tag?.toString()
                        enteredValue = edit.text?.toString()
                    }
                    resultClaimData.claimFieldoption.add(resultClaimTypeOption)
                }
                WIDGET_SPINNER -> {
                    val spinner =
                        rootView.findViewWithTag(claimTypeDetail.claimfield?.id) as Spinner
                    val selectedItem = spinner.selectedItem as ClaimFieldOption
                    val resultClaimTypeOption = ResultClaimTypeOption().apply {
                        checkedId = selectedItem.id
                        checkedName = selectedItem.name
                    }
                    resultClaimData.claimFieldoption.add(resultClaimTypeOption)
                }
            }
        }
        return resultClaimData
    }

    /**
     * Set text field input type based on claim type
     */
    private fun getInputType(inputType: String?): Int {
        var type = 0
        when (inputType) {
            WIDGET_EDIT_TEXT_NUMERIC -> type = InputType.TYPE_CLASS_NUMBER
            WIDGET_EDIT_TEXT_SINGLE_LINE, WIDGET_EDIT_TEXT_ALL_CAPS -> type =
                InputType.TYPE_CLASS_TEXT
        }
        return type
    }

    private fun getDependentItem() {
        dependantData = claims.claimtypedetail.filter { claimTypeDetail ->
            claimTypeDetail.claimfield?.isdependant.equals("1")
        }
    }
}
