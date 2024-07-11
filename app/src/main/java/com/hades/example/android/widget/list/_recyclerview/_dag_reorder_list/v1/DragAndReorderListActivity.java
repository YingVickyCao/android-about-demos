package com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v1;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.R;

/**
 * RecyclerView进阶：使用ItemTouchHelper实现拖拽和侧滑删除效果
 * http://www.codebaoku.com/tech/tech-yisu-217065.html
 * https://en.proft.me/2017/12/14/how-add-swipe-and-dragdrop-support-recyclerview/
 */
public class DragAndReorderListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_recyclerview_4_drag_reorder_list_activity_v1);

        showFragment(new DragAndReorderListFragment());
    }
}
