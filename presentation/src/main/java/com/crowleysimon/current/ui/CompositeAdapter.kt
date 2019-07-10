package com.crowleysimon.current.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic adapter that can display any [RecyclerView.ViewHolder] that implements [Bindable]
 * To use a [RecyclerView.ViewHolder] with the adapter they first must be added to the bindings using [addBinding]
 */
class CompositeAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Bindings<T> {
        fun isForItem(item: T): Boolean
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(item: T, holder: RecyclerView.ViewHolder)
    }

    var items: List<T> = emptyList()
    var bindings: MutableList<Bindings<T>> = mutableListOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return bindings.indexOfFirst { it.isForItem(items[position]) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return bindings[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bindings[holder.itemViewType].onBindViewHolder(items[position], holder)
    }
}

/**
 * Inline function used to bind a [RecyclerView.ViewHolder] to the adapter.
 * [T] is the Model of the Adapter and [I] is the Model of the view holder [H], [I] must extend from [T] to be able to
 * be bound to the adapter.
 */
inline fun <T: Any, reified I, reified H> CompositeAdapter<T>.addBinding(crossinline viewHolderFactory: (ViewGroup) -> H) where I: T, H: RecyclerView.ViewHolder, H: Bindable<I> {
    this.bindings.add(object: CompositeAdapter.Bindings<T> {
        override fun isForItem(item: T): Boolean {
            return item is I
        }

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            return viewHolderFactory(parent)
        }

        override fun onBindViewHolder(item: T, holder: RecyclerView.ViewHolder) {
            val viewHolder = holder as H
            viewHolder.bind(item as I)
        }
    })
}

interface Bindable<Model> {

    /** Passes the [Model] to the [RecyclerView.ViewHolder] */
    fun bind(model: Model)
}