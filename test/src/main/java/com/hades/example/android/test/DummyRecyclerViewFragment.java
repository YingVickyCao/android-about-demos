package com.hades.example.android.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.java.lib.DummyItem;
import com.hades.example.java.lib.DummyItems;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link IItemClickAction}
 * interface.
 */
public class DummyRecyclerViewFragment extends Fragment implements IItemClickAction {
    private static final String TAG = DummyRecyclerViewFragment.class.getSimpleName();

    private static final int ARG_COLUMN_COUNT = 1;
//    private static final  int ARG_COLUMN_COUNT =2;

    private RecyclerView mRecyclerView;
    private DummyRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
    private SwipeHelper mSwipeHelper;

    public DummyRecyclerViewFragment() {
    }

    @SuppressWarnings("unused")
    public static DummyRecyclerViewFragment newInstance(int columnCount) {
        return new DummyRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_recyclerview_dummy, container, false);
        mRecyclerView = view.findViewById(R.id.list);

        initList(view);
        init();
        return view;
    }

    private void initList(View view) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new DummyRecyclerViewAdapter(DummyItems.ITEMS(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.setListener(null);
    }

    @Override
    public void onItemClickListener(DummyItem item) {
        Toast.makeText(getContext(), item.colo2, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        mSwipeHelper = new SwipeHelper(getContext(), mRecyclerView, 250) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {

                buffer.add(new MyButton(getContext(), R.drawable.ic_launcher_foreground, Color.parseColor("#F24C05"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
//                                mAdapter.callDeleteFunction(pos);
//                                Toast.makeText(getContext(), "Click delete btn", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));

                buffer.add(new MyButton(getContext(), R.drawable.ic_launcher_background, Color.parseColor("#B9D40B"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
//                                mAdapter.callEditFunction(pos);
//                                Toast.makeText(getContext(), "Click Edit btn", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
            }
        };
    }
}