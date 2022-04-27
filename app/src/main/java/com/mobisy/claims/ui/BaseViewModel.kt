package com.mobisy.claims.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    //If we need to show empty status we can use
    val noData by lazy { ObservableField(false) }
}