package ru.gozerov.core

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T>: RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract var data: List<T>

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = data[position]
        holder.itemView.tag = item
        holder.bind(item)
    }

}

fun <T>RecyclerView.setupAdapter(
    adapter: BaseRecyclerViewAdapter<T>,
    data: List<T>,
    layoutManager: LayoutManagerRV
) {
    val adapterLayoutManager = when(layoutManager) {
        is LayoutManagerRV.LINEAR -> {
            val adapterOrientation = when (layoutManager.orientation) {
                is ORIENTATION.HORIZONTAL -> LinearLayoutManager.HORIZONTAL
                is ORIENTATION.VERTICAL -> LinearLayoutManager.VERTICAL
            }
            LinearLayoutManager(this.context, adapterOrientation, false)
        }
        is LayoutManagerRV.GRID -> {
            val adapterOrientation = when (layoutManager.orientation) {
                is ORIENTATION.HORIZONTAL -> GridLayoutManager.HORIZONTAL
                is ORIENTATION.VERTICAL -> GridLayoutManager.VERTICAL
            }
            GridLayoutManager(this.context, layoutManager.columns, adapterOrientation, false)
        }
    }
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

    adapter.data = data
    this.adapter = adapter
    this.layoutManager = adapterLayoutManager
}

sealed class ORIENTATION {
    object HORIZONTAL : ORIENTATION()
    object VERTICAL : ORIENTATION()
}

sealed class LayoutManagerRV {
    class LINEAR(val orientation: ORIENTATION): LayoutManagerRV()
    class GRID(val columns: Int, val orientation: ORIENTATION): LayoutManagerRV()
}
