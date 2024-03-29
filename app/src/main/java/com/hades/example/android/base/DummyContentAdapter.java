package com.hades.example.android.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hades.utility.android.R;
import com.hades.utility.jvm.DummyItem;

import java.util.List;

public class DummyContentAdapter extends BaseAdapter {
    private static final String TAG = "DummyContentAdapter";
    private List<DummyItem> mList;
    private Context mContext;

    public DummyContentAdapter(List<DummyItem> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public DummyItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dummy_content_item_view, null);
            Log.d(TAG, "getView: position=" + position + ",convertView=null");
            viewHolder = new ViewHolder();
            viewHolder._id = convertView.findViewById(R.id._id);
            viewHolder.col2 = convertView.findViewById(R.id.col2);
            viewHolder.col3 = convertView.findViewById(R.id.col3);

            // PO: [ListView][Adapter] convertView.setTag(viewHolder)
            convertView.setTag(viewHolder);

        } else {
            Log.d(TAG, "getView: position=" + position + ",convertView from tag");
            // PO: [ListView][Adapter] viewHolder = (ViewHolder) convertView.getTag()
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DummyItem model = getItem(position);

        viewHolder._id.setText(String.valueOf(model.getId()));
        viewHolder.col2.setText(model.getColo2());
        viewHolder.col3.setText(String.valueOf(model.getCol3()));
        return convertView;
    }

    private static class ViewHolder {
        TextView _id;
        TextView col2;
        TextView col3;
    }
}
