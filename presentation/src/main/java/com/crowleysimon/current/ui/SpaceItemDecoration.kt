package com.crowleysimon.current.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        when ((parent.layoutManager as LinearLayoutManager).orientation) {
            RecyclerView.HORIZONTAL -> {
                outRect.left = space
                outRect.right = if (position == state.itemCount - 1) outRect.left else 0
            }
            RecyclerView.VERTICAL -> { // TODO: Actual status bar height instead of space * 2
                outRect.top = if (position == 0) space * 2 else space
                outRect.bottom = if (position == state.itemCount - 1) outRect.top else 0
            }
        }
    }
}