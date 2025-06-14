package com.example.kotlin.test.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kotlin.test.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        textView.setText(text);
    }

    static MenuViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MenuViewHolder(view);
    }
}
