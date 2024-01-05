package com.hades.example.android.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.hades.utility.android.R;
import com.hades.utility.jvm.DummyItem;

import java.util.ArrayList;
import java.util.Collection;

//     private void addFragment() {
//        ArrayList<DummyItem> list = new ArrayList<>();
//
//        for (int i = 0; i <= 100; i++) {
//            list.add(new DummyItem(i, String.valueOf(i), i));
//        }
//        getSupportFragmentManager().beginTransaction().add(R.id.root, DummyContentFragment.getInstance(list), "test").commit();
//    }
public class DummyContentFragment extends BaseFragment {
    public static final String TAG = DummyContentFragment.class.getSimpleName();
    public static final String KEY_SEARCH_RESULT = "search_result";

    ArrayList<DummyItem> mList = new ArrayList<>();
    private ListView mListView;
    private DummyContentAdapter mAdapter;

    public static DummyContentFragment getInstance(ArrayList<DummyItem> list) {
        DummyContentFragment fragment = new DummyContentFragment();

        Bundle data = new Bundle();
        data.putSerializable(KEY_SEARCH_RESULT, list);
        fragment.setArguments(data);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_content, container, false);

        if (null != getArguments()) {
            mList.addAll((Collection<? extends DummyItem>) getArguments().getSerializable(KEY_SEARCH_RESULT));
        }
        mListView = view.findViewById(R.id.listView);

        mAdapter = new DummyContentAdapter(mList, getActivity());
        mListView.setAdapter(mAdapter);
        return view;
    }

    public void setList(ArrayList<DummyItem> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}