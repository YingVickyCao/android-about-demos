package com.hades.example.android.app_component.ipc.common.content_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hades.example.android.app_component.ipc.common.R;

import java.util.List;

public class DictAdapter extends BaseAdapter {
    final List<DictRowBean> mData;
    private LayoutInflater inflater;
    private DicListener listener;

    public DictAdapter(List<DictRowBean> data, DicListener listener, Context context) {
        mData = data;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public DictRowBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        DictRowBean bean = getItem(position);

        // 1. 布局复用 (ViewHolder 模式)
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_provider_dict_search_result_popup_item_view, null); // 假设有这个布局
            holder = new ViewHolder();
            holder.id = convertView.findViewById(R.id.id);
            holder.word = convertView.findViewById(R.id.word);
            holder.detail = convertView.findViewById(R.id.detail);
            holder.update = convertView.findViewById(R.id.update);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.id.setText(String.valueOf(bean.get_id()));
        holder.word.setText(bean.getWord());
        holder.detail.setText(bean.getDetail());
        holder.update.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdate(position, bean.getWord(), bean.get_id());
            }
        });
        holder.delete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(position, bean.get_id());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView id;
        TextView word;
        TextView detail;
        Button update;
        Button delete;
    }
}
