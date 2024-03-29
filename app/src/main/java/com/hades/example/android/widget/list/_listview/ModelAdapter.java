/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.hades.example.android.widget.list._listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hades.example.android.R;

import java.util.List;

public class ModelAdapter extends ArrayAdapter<Model> {

    private LayoutInflater mInflater;

    ModelAdapter(Context context, int textViewResourceId, List<Model> objects) {
        super(context, textViewResourceId, objects);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        // 如果 convertView 为 null，就填充视图
        if (convertView == null) {
            // 创建视图
//            convertView = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//            convertView = mInflater.inflate(R.layout.list_item_view_4, parent, false); // Recommended. item height = 64dp

            /**
             * Not Recommended.
             * item height != 64dp.  Wrong
             *
             * root = null -> LayoutParams ViewGroup generate a default set.
             *
             * android:layout_XXX attributes are always be evaluated in the context of the parent view.
             * As a result, without any parent, all LayoutParams declared on the root element of XML tree will just get thrown away.
             * Without LayoutParams, the ViewGroup that eventually hosts the inflated layout is left to generate a default set for app..
             */
//            convertView = mInflater.inflate(R.layout.list_item_view_4, null);
//            convertView = mInflater.inflate(R.layout.list_item_view_5, null); // item height != 64dp. Wrong
            convertView = mInflater.inflate(R.layout.list_item_view_6, null);// item height = 64dp. OK

            viewHolder = new ViewHolder();
            // 获取视图中各个控件的引用，并存入viewHolder中
            viewHolder.text1 = convertView.findViewById(android.R.id.text1);

            // 把ViewHolder存入标记中
            convertView.setTag(viewHolder);

        } else {
            // 如果convertView 非null， 则回收利用它。
            // convertView的标记中获取viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 根据视图的索引位置，获取 model 对象
        Model model = getItem(position);

        // 填充视图
        // 使用 model 中的数据填充该视图
        viewHolder.text1.setText(model.getText1());

        return convertView;
    }

    // ViewHolder 类
    // ViewHolder中变量存储所有UI控件的引用
    private static class ViewHolder {
        TextView text1;
    }
}
