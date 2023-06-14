package com.hades.example.android._feature.menu_manager.menu_page;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private static final String TAG = MenuAdapter.class.getSimpleName();

    List<Group> mList;
    private onMenuClick mDragView;
    private Activity mContext;
    private List<VHPositionHashCodeBean> mViewHolderPositionHashCodeList = new ArrayList<>();
    private VHPositionHashCodeBean mCheckContain = new VHPositionHashCodeBean();
    private int minPos = 0;
    private int maxPos = 0;
    private OpenedPage mOpenedPage;

    public MenuAdapter(List<Group> list, Activity context) {
        mList = list;
        mContext = context;
    }

    public void setOpenedPage(OpenedPage openedPage) {
        mOpenedPage = openedPage;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_page_parent_menu, parent, false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(view);
        mViewHolderPositionHashCodeList.add(new VHPositionHashCodeBean(0, menuViewHolder.hashCode()));
        Log.d(TAG, "onCreateViewHolder: @ItemViewHolder=" + menuViewHolder.hashCode() + ",vh list=" + mViewHolderPositionHashCodeList.size());
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        mCheckContain.hashCode = holder.hashCode();
        if (mViewHolderPositionHashCodeList.contains(mCheckContain)) {
            int index = mViewHolderPositionHashCodeList.indexOf(mCheckContain);
            if (-1 != index) {
                VHPositionHashCodeBean bean = mViewHolderPositionHashCodeList.get(index);
                bean.position = position;
            }
        } else {
            mViewHolderPositionHashCodeList.add(new VHPositionHashCodeBean(position, holder.hashCode()));
        }

        Log.d(TAG, "onBindViewHolder: position=" + position + ",@ItemViewHolder=" + holder.hashCode() + ",vh list=" + mViewHolderPositionHashCodeList.size());

        Group bean = mList.get(position);
        boolean isSelected = null != mOpenedPage && null != mOpenedPage.getTitle() && mOpenedPage.getTitle().equals(bean.getTitle());
        holder.check.setSelected(isSelected);
        holder.check.setTag(isSelected);
        holder.groupTitle.setText(bean.getTitle());
        onBindViewHolder4ChildContainer(holder, position, bean);
        if (holder.childContainer.getChildCount() > 0) {
            holder.groupContainer.setOnClickListener(v -> toggleExpand(holder.childContainer, bean, position));
        } else {
            holder.groupContainer.setOnClickListener(v -> openPage(bean.getTitle(), null, bean, true));
        }
    }

    private void printMotionEvent(MotionEvent event) {
        Log.d(TAG, "printMotionEvent: event action=" + event.getAction() + ",rawX=" + event.getRawX() + ",rawY=" + event.getRawY());
    }

    private void onBindViewHolder4ChildContainer(final MenuViewHolder holder, int position, Group bean) {
        List<Child> children = bean.getChildren();
        if (null == children || children.isEmpty()) {
            holder.childContainer.setVisibility(View.GONE);
            return;
        }

        holder.childContainer.setVisibility(View.VISIBLE);
        int dataChildCount = children.size();
        int viewChildCount = holder.childContainer.getChildCount();

        String compare = "";
        if (viewChildCount < dataChildCount) {
            compare = "<";
        } else if (viewChildCount == dataChildCount) {
            compare = "=";
        } else {
            compare = ">";
        }

        Log.d(TAG, "onBindViewHolder4ChildContainer: position=" + position + ",dataChildCount=" + dataChildCount + "  " + compare + "  " + "viewChildCount=" + viewChildCount);

        if (viewChildCount < dataChildCount) {
            for (int i = 0; i < dataChildCount - viewChildCount; i++) {
                Log.d(TAG, "onBindViewHolder4ChildContainer: <  " + " add child " + (i + 1));
                View child = LayoutInflater.from(mContext).inflate(R.layout.menu_page_child_menu, null);
                holder.childContainer.addView(child);
            }

        } else if (viewChildCount == dataChildCount) {

        } else {
            try {
                holder.groupContainer.removeViews(dataChildCount, viewChildCount - dataChildCount - 1);
            } catch (IndexOutOfBoundsException ex) {
                Log.d(TAG, "onBindViewHolder4ChildContainer: IndexOutOfBoundsException");
            }

        }

        Log.d(TAG, "onBindViewHolder4ChildContainer:dataChildCount=" + dataChildCount + ",viewChildCount=" + holder.childContainer.getChildCount());

        for (int i = 0; i < dataChildCount; i++) {
            View child = holder.childContainer.getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            TextView textView = child.findViewById(R.id.child_menu_title);
            String childText = String.valueOf(children.get(i).childText);
            textView.setText(childText);
            if (null != mOpenedPage && null != mOpenedPage.getChildTitle() && childText.equals(mOpenedPage.getChildTitle())) {
                textView.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
            } else {
                textView.setTextColor(mContext.getResources().getColor(android.R.color.black));
            }
            Log.d(TAG, "onBindViewHolder4ChildContainer: childText=" + childText);
            textView.setOnClickListener(v -> openPage(bean.getTitle(), childText, bean, false));
        }
        holder.childContainer.applyStatus();
    }

    private void resetPositionRegion() {
        minPos = 0;
        maxPos = 0;
    }

    @Override
    public long getItemId(int position) {
        return (mList.get(position).getId()) - 1;
    }

    private void openPage(String title, String childTitle, Group bean, boolean isGroup) {
        if (null != mDragView) {
            mDragView.openPage(bean, isGroup, title, childTitle);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void toggleExpand(ToggleChildContainer view, final Group bean, int position) {
        view.toggleExpandStatus();
    }

    private boolean isHasExpand() {
        return minPos != 0 || maxPos != 0;
    }

    private void collapse() {
//        mContext.runOnUiThread(() -> notifyItemRangeChanged(minPos, maxPos - minPos + 1));(() -> notifyItemRangeChanged(minPos, maxPos - minPos + 1));
        mContext.runOnUiThread(this::notifyDataSetChanged);
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder implements IItemViewHolder {
        private View root;
        private TextView groupTitle;
        private ImageView check;
        private ToggleChildContainer childContainer;
        private ViewGroup groupContainer;

        MenuViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            groupTitle = itemView.findViewById(R.id.groupTitle);
            check = itemView.findViewById(R.id.check);
            childContainer = itemView.findViewById(R.id.childContainer);
            groupContainer = itemView.findViewById(R.id.groupContainer);
        }

        // QA: onItemSelected - show item view bg when drag dragBtn
        @Override
        public void onItemSelected() {
            root.setSelected(true);
            updateCheck();
        }

        private void updateCheck() {
            if (null != check.getTag() && check.getTag() instanceof Boolean) {
                boolean selected = (boolean) check.getTag();
                check.setSelected(selected);
            }
        }
    }
}