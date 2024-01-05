package com.hades.example.android.widget.view_animator.adapterviewflipper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hades.example.android.R;

class CustomAdapter extends BaseAdapter {
    Context context;
    int[] images;
    String[] names;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] names, int[] images) {
        this.context = context;
        this.images = images;
        this.names = names;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_adapter_layout, null);

        // Link those objects with their respective id's
        // that we have given in .XML file
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        // Set the data in text view
        name.setText(names[position]);

        // Set the image in Image view
        image.setImageResource(images[position]);
        return view;
    }
}