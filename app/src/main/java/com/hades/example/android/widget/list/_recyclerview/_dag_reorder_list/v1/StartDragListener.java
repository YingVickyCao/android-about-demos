package com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v1;

import androidx.recyclerview.widget.RecyclerView;

public interface StartDragListener {
    void startDrag(RecyclerView.ViewHolder viewHolder);

    void startSwipe(RecyclerView.ViewHolder viewHolder);
}