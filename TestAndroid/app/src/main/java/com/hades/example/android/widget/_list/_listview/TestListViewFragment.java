
package com.hades.example.android.widget._list._listview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class TestListViewFragment extends Fragment {
    private static final String TAG = TestListViewFragment.class.getSimpleName();

    //    private static final int MODEL_COUNT = 1000;
    private static final int MODEL_COUNT = 5;
    private ListView mListView;
    private ViewGroup mItemsRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reuse_adapter_item_view_in_list_view, container, false);
        // ERROR:java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
//        View view = inflater.inflate(R.layout.fragment_reuse_adapter_item_view_in_list_view, container);
        mItemsRoot = view.findViewById(R.id.itemsRoot);
        mListView = view.findViewById(R.id.listView);
        mListView.setAdapter(new ModelAdapter(getActivity(), 0, buildModels()));
        forbidScroll();
        fillItems();
        return view;
    }

    private void forbidScroll() {
        mListView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                Log.d(TAG, "onTouch: ACTION_MOVE");
                return true;
            }
            return false;
        });
    }

    private List<Model> buildModels() {
        final ArrayList<Model> ret = new ArrayList<>(MODEL_COUNT);
        for (int i = 0; i < MODEL_COUNT; i++) {
            final Model model = new Model();
            model.setImage(R.drawable.ic_launcher);
            model.setText1("Name " + i);
            model.setText2("Description " + (i + 1));
            ret.add(model);
        }
        return ret;
    }

    private void fillItems() {
        for (int i = 0; i < 3; i++) {
//            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_view_7, null); // Wrong
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_view_7, mItemsRoot, false); //OK
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(String.valueOf(i + 1));
            mItemsRoot.addView(view);
        }
    }
}