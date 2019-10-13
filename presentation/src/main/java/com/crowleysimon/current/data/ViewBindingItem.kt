package com.crowleysimon.current.data

import android.view.View
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.Item

abstract class ViewBindingItem<T : ViewBinding> : Item<ViewBindingHolder<T>> {

    constructor() : super()

    constructor(id: Long) : super(id)

    override fun createViewHolder(itemView: View): ViewBindingHolder<T> = ViewBindingHolder(inflate(itemView))

    override fun bind(viewHolder: ViewBindingHolder<T>, position: Int) {
        throw RuntimeException("Doesn't get called")
    }

    override fun bind(viewHolder: ViewBindingHolder<T>, position: Int, payloads: MutableList<Any>) {
        bind(viewHolder.binding, position, payloads)
    }

    abstract fun inflate(itemView: View): T

    abstract fun bind(viewBinding: T, position: Int)

    private fun bind(viewBinding: T, position: Int, payloads: List<Any>) {
        bind(viewBinding, position)
    }
}