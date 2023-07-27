package com.hades.example.android._feature.menu_manager.menu_page;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;
import com.hades.example.android._feature.menu_manager.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private static final String TAG = MenuAdapter.class.getSimpleName();

    List<Menu> mList;
    private onMenuClick clickCallback;
    private final Activity mContext;

    public MenuAdapter(Activity context) {
        mContext = context;
    }

    public void setList(List<Menu> mList) {
        this.mList = mList;
    }

    public void setClickListener(onMenuClick clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_page_parent_menu, parent, false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: @ItemViewHolder=" + menuViewHolder.hashCode() + ",vh list=");
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: position=" + position + ",@ItemViewHolder=" + holder.hashCode());

        Menu bean = mList.get(position);
        holder.groupTitle.setText(bean.getTitle());
        onBindViewHolder4ChildContainer(holder, position, bean);
        if (holder.childContainer.getChildCount() > 0) {
            holder.groupContainer.setOnClickListener(v -> toggleExpand(holder.childContainer));
        } else {
            holder.groupContainer.setOnClickListener(v -> openPage(bean));
        }
    }


    private void onBindViewHolder4ChildContainer(final MenuViewHolder holder, int position, Menu bean) {
        List<Menu> children = bean.getItems();
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
            Menu chileMenu = children.get(i);
            String childText = chileMenu.getTitle();
            textView.setText(childText);
            Log.d(TAG, "onBindViewHolder4ChildContainer: childText=" + childText);
            textView.setOnClickListener(v -> openPage(chileMenu));
        }
        holder.childContainer.applyStatus();
    }

    @Override
    public long getItemId(int position) {
        return (mList.get(position).getId()) - 1;
    }

    private void openPage(Menu bean) {
        if (null != clickCallback) {
            clickCallback.openPage(bean);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void toggleExpand(ToggleChildContainer view) {
        view.toggleExpandStatus();
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder implements IItemViewHolder {
        private final View root;
        private final TextView groupTitle;
        private final ImageView check;
        private final ToggleChildContainer childContainer;
        private final ViewGroup groupContainer;

        MenuViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.root);
            groupTitle = itemView.findViewById(R.id.groupTitle);
            check = itemView.findViewById(R.id.check);
            groupContainer = itemView.findViewById(R.id.groupContainer);
            childContainer = itemView.findViewById(R.id.childContainer);
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