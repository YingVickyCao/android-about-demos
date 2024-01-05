package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

class ViewFlipperAdapter extends BaseAdapter {
    private static final String TAG = "ViewFlipperAdapter";
    Context context;

    List<FlipperItem> items = new ArrayList<>();
    LayoutInflater inflater;

    public ViewFlipperAdapter(Context context, List<FlipperItem> items) {
        this.context = context;
        this.items.addAll(items);
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public FlipperItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            Log.d(TAG, "getView:convertView=null, position=" + position);
            convertView = inflater.inflate(R.layout.custom_adapter_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView:convertView from tag, position=" + position);
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FlipperItem model = getItem(position);
        viewHolder.name.setText(model.getName());
        viewHolder.image.setImageResource(model.getResId());
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        ImageView image;
    }
}