package com.angelsheaven.teremdemoapp.widgets;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.angelsheaven.teremdemoapp.ui.listNews.NewsAdapter;

public  class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private NewsAdapter mAdapter;

    public SwipeToDeleteCallback(NewsAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.markRead(position);
    }

}
