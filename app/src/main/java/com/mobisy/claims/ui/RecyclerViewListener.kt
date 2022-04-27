package com.mobisy.claims.ui

interface RecyclerViewListener {
    fun onBindData(dataBinding: Any, model: Any, position: Int)

    fun onItemClick(model: Any, position: Int)
}