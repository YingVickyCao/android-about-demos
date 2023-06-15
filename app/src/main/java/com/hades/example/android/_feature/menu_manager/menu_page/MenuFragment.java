package com.hades.example.android._feature.menu_manager.menu_page;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends DialogFragment {
    private static final String TAG = "MenuFragment";

    private List<Group> menuData;

    private MenuAdapter mAdapter;
    private OpenedPage mOpenedPage = new OpenedPage();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentShowAsFullDialog);

        initList1Data();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_page, container, false);
        RecyclerView listView = view.findViewById(R.id.listView);
        mAdapter = createAdapter(menuData);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(getLinearLayoutManager());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (null != dialog) {
            Log.d(TAG, "onStart: dialog != null"); // Show as a Dialog
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (null != dialog.getWindow()) {
                dialog.getWindow().setLayout(width, height);                                        // 3)
                dialog.getWindow().setWindowAnimations(R.style.DialogFragmentShowAsFullDialog);
            }
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                /**
                 * https://stackoverflow.com/questions/7622031/dialogfragment-and-back-button
                 * 通过判断各种事件，处理监听
                 */
                if (keyCode == KeyEvent.KEYCODE_BACK && KeyEvent.ACTION_DOWN == event.getAction()) {
                    Log.d(TAG, "onKey: Back");
                    dismiss();
                    return true; // pretend we've processed it. 中断事件，不接受按键信息
                } else {
                    Log.d(TAG, "onKey: Others");
                    return false; // pass on to be processed as normal 事件继续向下传递
                }
            });
        } else {
            Log.d(TAG, "onStart: dialog = null"); // Show as an Embedded Fragment
        }
    }

    private MenuAdapter createAdapter(List<Group> list1) {
        MenuAdapter adapter = new MenuAdapter(list1, getActivity());
        adapter.setOpenedPage(mOpenedPage);
        adapter.setHasStableIds(true);
        return adapter;
    }

    private LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        return linearLayoutManager;
    }

    private void initList1Data() {
        menuData = initListData("Group 1 - Child ", 20);
    }

    private List<Group> initListData(String childPre, int num) {
        List<Group> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            List<Child> childList = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                childList.add(new Child(childPre + (i + 1) + "_" + (j + 1)));
                if (j >= 4) {
                    break;
                }
            }
            Group message = new Group(String.valueOf(i + 1), (i + 1), childList);
            list.add(message);
        }
        return list;
    }
}
