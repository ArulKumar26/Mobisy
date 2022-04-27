package com.mobisy.claims.ui

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobisy.claims.extensions.inflate

open class RecyclerViewAdapter(
    private var layoutId: Int,
    var callBack: RecyclerViewListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    private val items = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val viewDataBinding = parent.inflate(layoutId)
        return ItemViewHolder(viewDataBinding)
    }

    /**
     *  Function to bind each item in the items list to the RecyclerView at a given position
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    /**
     *  Function to get the total number of items in the RecyclerView
     */
    override fun getItemCount(): Int = items.size

    /**
     *  Function to add a list of items to the RecyclerView
     */
    fun addItems(items: List<Any>) = this.items.addAll(items)

    /**
     *  Function to clear the RecyclerView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    /**
     *  The ViewHolder class which binds the data to the RecyclerView item
     */
    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var dataBinding: Any = DataBindingUtil.bind(itemView)!!

        fun bindData(data: Any) {
            callBack.onBindData(dataBinding, data, adapterPosition)
            setItemClick(data)
        }

        private fun setItemClick(model: Any) {
            itemView.setOnClickListener {
                callBack.onItemClick(model, adapterPosition)
            }
        }
    }
}