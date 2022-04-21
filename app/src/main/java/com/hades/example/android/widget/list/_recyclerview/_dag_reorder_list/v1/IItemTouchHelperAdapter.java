package com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v1;

public interface IItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}