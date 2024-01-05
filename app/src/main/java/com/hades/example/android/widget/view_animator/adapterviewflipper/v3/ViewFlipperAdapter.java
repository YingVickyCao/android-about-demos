package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.collection.SparseArrayCompat;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewFlipperAdapter<T> extends BaseAdapter {
    private static final String TAG = "ViewFlipperAdapter";
    Context context;

    List<T> items = new ArrayList<>();
    LayoutInflater inflater;

    public ViewFlipperAdapter(Context context, List<T> items) {
        this.context = context;
        this.items.addAll(items);
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(convertView, inflater, R.layout.custom_adapter_layout);
        convert(viewHolder, items.get(position));
        return viewHolder.getConvertView();
    }

    protected abstract void convert(ViewHolder baseViewHolder, T t);

    public static class ViewHolder {
        private View convertView;
        private SparseArrayCompat<View> viewIDs = new SparseArrayCompat<>();

        public ViewHolder(View convertView) {
            this.convertView = convertView;
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

        public View getViewById(@IdRes int viewId) {
            View view = viewIDs.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                viewIDs.put(viewId, view);
            }
            return view;
        }
    }
}