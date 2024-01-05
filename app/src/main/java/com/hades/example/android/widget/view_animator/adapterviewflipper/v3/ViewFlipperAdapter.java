package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

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
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView, inflater, R.layout.custom_adapter_layout);
        FlipperItem model = getItem(position);
        viewHolder.name.setText(model.getName());
        viewHolder.image.setImageResource(model.getResId());
        return viewHolder.getConvertView();
    }

    private static class ViewHolder {
        private View convertView;
        private TextView name;
        private ImageView image;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
            image = (ImageView) convertView.findViewById(R.id.image);
            name = (TextView) convertView.findViewById(R.id.name);
        }

        public static ViewHolder getViewHolder(View convertView, LayoutInflater inflater, @LayoutRes int resource) {
            ViewHolder viewHolder;
            if (null == convertView) {
                convertView = inflater.inflate(resource, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            return viewHolder;
        }

        public View getConvertView() {
            return convertView;
        }
    }
}