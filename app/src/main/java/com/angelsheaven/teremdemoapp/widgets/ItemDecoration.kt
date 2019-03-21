package com.angelsheaven.teremdemoapp.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration() : RecyclerView.ItemDecoration() {
    private var offset: Int = 0

    constructor(_offset: Int) : this() {
        offset = _offset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = offset
            }
            left = offset
            right = offset
            bottom = offset
        }
    }
}