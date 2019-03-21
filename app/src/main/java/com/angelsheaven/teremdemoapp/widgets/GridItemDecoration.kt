package com.angelsheaven.teremdemoapp.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridItemDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect){
            if(parent.getChildAdapterPosition(view)%2==0){
                left = padding
            }
            bottom = padding
            top = padding
            right = padding
        }

    }
}
