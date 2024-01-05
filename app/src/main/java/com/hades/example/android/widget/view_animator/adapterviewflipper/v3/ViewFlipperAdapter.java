package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

import com.hades.example.android.app_component._activity._life_cycle.A;
import com.hades.example.android.tools.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewFlipperAdapter<T> extends BaseAdapter {
    private static final String TAG = "ViewFlipperAdapter";
    @LayoutRes
    int itemLayoutResId;
    List<T> items = new ArrayList<>();
    LayoutInflater inflater;
    Activity context;

    private int itemWidth = ViewGroup.LayoutParams.MATCH_PARENT;

    public ViewFlipperAdapter(@NonNull Activity context, @LayoutRes int itemLayoutResId, @NonNull List<T> items) {
        this.context = context;
        this.itemLayoutResId = itemLayoutResId;
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
        ViewHolder viewHolder = ViewHolder.getViewHolder(context, convertView, itemLayoutResId, parent);
        convert(viewHolder, items.get(position));
        setItemLayoutParams(viewHolder);
        return viewHolder.getConvertView();
    }

    private void setItemLayoutParams(ViewHolder viewHolder) {
        // 必须设置宽度，否则宽度为内容大小
        if (itemWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
            viewHolder.getConvertView().getLayoutParams().width = DensityUtil.getWidthSize(context);
        } else if (itemWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
            viewHolder.getConvertView().getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (itemWidth > 0) {
            viewHolder.getConvertView().getLayoutParams().width = itemWidth;
        } else {
            viewHolder.getConvertView().getLayoutParams().width = DensityUtil.getWidthSize(context);
        }
    }

    protected abstract void convert(ViewHolder baseViewHolder, T t);


    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public static class ViewHolder {
        private View convertView;
        private SparseArrayCompat<View> viewIDs = new SparseArrayCompat<>();

        public ViewHolder(View convertView) {
            this.convertView = convertView;
        }

        public static ViewHolder getViewHolder(Context context, View convertView, @LayoutRes int resource, @Nullable ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                Log.d(TAG, "getViewHolder: convertView=null");
                convertView = LayoutInflater.from(context).inflate(resource, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                Log.d(TAG, "getViewHolder: convertView from tag");
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Log.d(TAG, "getViewHolder:convertView().getLayoutParams()=" + viewHolder.getConvertView().getLayoutParams());
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