package com.example.kotlin.test.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.kotlin.test.db.Menu;

public class MenuListAdapter extends ListAdapter<Menu, MenuViewHolder> {
    protected MenuListAdapter(@NonNull DiffUtil.ItemCallback<Menu> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MenuViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu current = getItem(position);
        holder.bind(current.menuTitle);
    }


    // DiffUtil is a utility class that calculates the difference between two lists and outputs a list of update operations that converts the first list into the second one.
    //It can be used to calculate updates for a RecyclerView Adapter.
    static class MenuDiff extends DiffUtil.ItemCallback<Menu> {

        @Override
        public boolean areItemsTheSame(@NonNull Menu oldItem, @NonNull Menu newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Menu oldItem, @NonNull Menu newItem) {
            return oldItem.menuTitle.equalsIgnoreCase(newItem.menuTitle);
        }
    }
}
