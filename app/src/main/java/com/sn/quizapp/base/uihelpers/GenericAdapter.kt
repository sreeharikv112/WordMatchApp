package com.sn.quizapp.base.uihelpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Common adapter for RecyclerViews.
 *
 */
abstract class GenericAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    var listItems: List<T>

    var mListener: View.OnClickListener? = null

    init {
        listItems = emptyList()
    }

    constructor(){
        listItems = emptyList()
    }

    constructor(listener: View.OnClickListener){
        listItems = emptyList()
        mListener = listener
    }

    fun setItems(listItems: List<T>) {
        this.listItems = listItems
        Log.d("GenericAdapter","listItems size== ${listItems.size}")
        notifyDataSetChanged()
    }

    fun refreshRecyclerView(){
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
            , viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as Binder<T>).bind(listItems[position])
        holder.itemView.tag = listItems[position]

        if(mListener != null)
        holder.itemView.setOnClickListener(mListener)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {

        return getLayoutId(position, listItems[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int):RecyclerView.ViewHolder

    internal interface Binder<T> {
        fun bind(data: T)
    }
}