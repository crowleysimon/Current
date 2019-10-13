package com.crowleysimon.current.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (space == 0) return

        val position = parent.getChildAdapterPosition(view)

        if (position == RecyclerView.NO_POSITION) return

        outRect.left = space

        if (position == state.itemCount - 1) {
            outRect.right = outRect.left
        }
    }
}