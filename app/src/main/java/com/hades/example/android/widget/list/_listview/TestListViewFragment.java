
package com.hades.example.android.widget.list._listview;

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
    private static final int MODEL_COUNT = 50;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reuse_adapter_item_view_in_list_view, container, false);
        // ERROR:java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
//        View view = inflater.inflate(R.layout.fragment_reuse_adapter_item_view_in_list_view, container);
        mListView = view.findViewById(R.id.listView);
        mListView.setAdapter(new ModelAdapter(getActivity(), 0, buildModels()));
//        forbidScroll();
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
}