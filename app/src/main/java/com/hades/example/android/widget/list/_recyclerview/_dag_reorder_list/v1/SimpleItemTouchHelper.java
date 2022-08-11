package com.hades.example.android.widget.list._recyclerview._dag_reorder_list.v1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;

/**
 * https://en.proft.me/2017/12/14/how-add-swipe-and-dragdrop-support-recyclerview/
 */
public class SimpleItemTouchHelper extends ItemTouchHelper.Callback {
    private static final String TAG = SimpleItemTouchHelper.class.getSimpleName();

    private ItemTouchHelperAdapter mAdapter;
    private Paint p;

    int removeBtnWidth;

    SimpleItemTouchHelper(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // 允许上下的拖动
//        int swipeFlags = ItemTouchHelper.LEFT;// 只允许从右向左侧滑
//        return makeMovementFlags(dragFlags, swipeFlags);
        return makeMovementFlags(getDragDirectionsFlags(viewHolder), swipeFlags());
    }

    private boolean isFirst(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getBindingAdapterPosition() == 0;
    }

    private boolean isLast(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getBindingAdapterPosition() == (mAdapter.list.size() - 1);
    }

    private int dragFlags4Last() {
        return ItemTouchHelper.UP;
    }

    private int dragFlags4First() {
        return ItemTouchHelper.DOWN;
    }

    private int dragFlags4Middle() {
        return ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    }

    private int swipeFlags() {
//        return ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;  // 允许向左、向右滑
        return ItemTouchHelper.LEFT;    // 只允许从右向左侧滑
    }

    private int getDragDirectionsFlags(RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        if (isFirst(viewHolder)) {
            Log.d(TAG, "getDragDirectionsFlags: first");
            dragFlags = dragFlags4First();
        } else if (isLast(viewHolder)) {
            dragFlags = dragFlags4Last();
            Log.d(TAG, "getDragDirectionsFlags: last");
        } else {
            dragFlags = dragFlags4Middle();
            Log.d(TAG, "getDragDirectionsFlags: middle");
        }
        return dragFlags;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    /**
     * 实现我们自定义的交互规则或者自定义的动画效果
     */
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (removeBtnWidth == 0) {
                removeBtnWidth = recyclerView.getResources().getDimensionPixelSize(R.dimen.size_80);
            }
            if (dX == 0) { //有时候点击也会被触发成swipe,这里判断不发生偏移量就跳过
                return;
            }

            ItemTouchHelperAdapter.ItemViewHolder itemViewHolder = (ItemTouchHelperAdapter.ItemViewHolder) viewHolder;
            if (dX < 0) {
                Log.d(TAG, "onChildDraw:[1]" + dX + ",removeBtnWidth=" + removeBtnWidth);
//                itemViewHolder.root.setScrollX(Math.min((int) Math.abs(dX), removeBtnWidth));
                itemViewHolder.root.setScrollX(removeBtnWidth);
            } else {
                Log.d(TAG, "onChildDraw:[2]" + dX + ",removeBtnWidth=" + removeBtnWidth);
                itemViewHolder.root.setScrollX(Math.max((int) (removeBtnWidth), 0));
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    /*
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Log.d(TAG, "onChildDraw: getAdapterPosition=" + viewHolder.getAdapterPosition() + ",getOldPosition=" + viewHolder.getOldPosition() + ",dx=" + dX + ",dy=" + dY + ",actionState=" + actionState + ",isCurrentlyActive=" + isCurrentlyActive);

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            Bitmap icon;

            if (p == null) {
                p = new Paint();
            }

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if (dX > 0) {
                p.setColor(Color.parseColor("#388E3C"));
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                c.drawRect(background, p);

                float left = (float) itemView.getLeft() + width;
                float top = (float) itemView.getTop() + width;
                float right = (float) itemView.getLeft() + 2 * width;
                float bottom = (float) itemView.getBottom() - width;

                icon = getBitmapFromVectorDrawable(recyclerView.getContext(), R.drawable.ic_svg_add);
                RectF iconDest = new RectF(left, top, right, bottom);

                c.drawBitmap(icon, null, iconDest, p);
            } else if (dX < 0) {
                Log.d(TAG, "onChildDraw:draw Delete ");
                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background, p);
                icon = getBitmapFromVectorDrawable(recyclerView.getContext(), R.drawable.ic_svg_delete);

                float left = (float) itemView.getRight() - 2 * width;
                float top = (float) itemView.getTop() + width;
                float right = (float) itemView.getRight() - width;
                float bottom = (float) itemView.getBottom() - width;
                RectF iconDest = new RectF(left, top, right, bottom);

                c.drawBitmap(icon, null, iconDest, p);
                viewHolder.itemView.findViewById(R.id.remove).setVisibility(View.VISIBLE);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            View itemView = viewHolder.itemView;
//            Log.d(TAG, "onChildDraw: left=" + itemView.getLeft() + ",top=" + (float) itemView.getTop() + ",right=" + itemView.getRight() + ",bottom=" + (float) itemView.getBottom());
            Log.d(TAG, "onChildDraw: top=" + itemView.getTop() + ",bottom=" + (float) itemView.getBottom() + ",height=" + recyclerView.getHeight());

            if (itemView.getTop() == 0 && viewHolder.getBindingAdapterPosition() == 0) {
                // If Drag (bottom -> top) to  fist , stop drop continue
                Log.d(TAG, "onChildDraw: top");
//            } else if (itemView.getBottom() == recyclerView.getHeight() && viewHolder.getAdapterPosition() == (recyclerView.getChildCount() - 1)) {
            } else if (itemView.getBottom() == recyclerView.getHeight()) {
                // If Drag (top -> bottom) to  last , stop drop continue
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                Log.d(TAG, "onChildDraw:findLastCompletelyVisibleItemPosition= " + layoutManager.findLastCompletelyVisibleItemPosition() + ",findLastVisibleItemPosition=" + layoutManager.findLastVisibleItemPosition());
//                boolean notAllVisible = layoutManager.findLastVisibleItemPosition() < recyclerView.getChildCount() - 1;
            } else {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }
    */

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Log.d(TAG, "onChildDrawOver: getAdapterPosition=" + viewHolder.getAdapterPosition() + ",getOldPosition=" + viewHolder.getOldPosition() + ",dx=" + dX + ",dy=" + dY + ",actionState=" + actionState + ",isCurrentlyActive=" + isCurrentlyActive);

        /*
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            View itemView = viewHolder.itemView;
//            Log.d(TAG, "onChildDraw: left=" + itemView.getLeft() + ",top=" + (float) itemView.getTop() + ",right=" + itemView.getRight() + ",bottom=" + (float) itemView.getBottom());
            Log.d(TAG, "onChildDraw: top=" + itemView.getTop() + ",bottom=" + (float) itemView.getBottom() + ",height=" + recyclerView.getHeight());

            if (itemView.getTop() == 0 && viewHolder.getAdapterPosition() == 0) {
                // If Drag (bottom -> top) to  fist , stop drop continue
                Log.d(TAG, "onChildDraw: top");
//            } else if (itemView.getBottom() == recyclerView.getHeight() && viewHolder.getAdapterPosition() == (recyclerView.getChildCount() - 1)) {
            } else if (itemView.getBottom() == recyclerView.getHeight()) {
                // If Drag (top -> bottom) to  last , stop drop continue
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                Log.d(TAG, "onChildDraw:findLastCompletelyVisibleItemPosition= " + layoutManager.findLastCompletelyVisibleItemPosition() + ",findLastVisibleItemPosition=" + layoutManager.findLastVisibleItemPosition());
                Log.d(TAG, "onChildDraw: bottom");
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }*/
    }

    /**
     * 实现上下方向拖拽时，交换位置
     */
    // |
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.d(TAG, "onMove: origin=" + viewHolder.getBindingAdapterPosition() + ",target pos=" + target.getBindingAdapterPosition());
        return mAdapter.onItemMove(viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());
    }

    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    @Override
    public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
//        return super.canDropOver(recyclerView, current, target);
        return false;
    }

    /**
     * 侧滑判定成功后 , 会调用该方法
     * 当用户左右滑动Item达到删除条件时，会调用该方法，一般手指触摸滑动的距离达到RecyclerView宽度的一半时，再松开手指，此时该Item会继续向原先滑动方向滑过去并且调用onSwiped方法进行删除，否则会反向滑回原来的位置
     */
    // ---
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d(TAG, "onSwiped: ");
        // 如果在onSwiped方法内我们没有进行任何操作，即不删除已经滑过去的Item，那么就会留下空白的地方，因为实际上该ItemView还占据着该位置，只是移出了我们的可视范围内罢了
        mAdapter.onItemDismiss(viewHolder.getBindingAdapterPosition());
    }

    /**
     * 支持长按拖动
     *
     * @return
     */
    // QA: Only drag btn can drag =  When long click item view, cannot drag. => false
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 支持左右滑动
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 从静止状态变为拖拽或者滑动的时候会回调该方法，参数actionState表示当前的状态
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d(TAG, "onSelectedChanged: actionState=" + actionState);

        if (viewHolder instanceof IItemTouchHelperViewHolder) {
            ((IItemTouchHelperViewHolder) viewHolder).onItemSelected();
        }
    }

    /**
     * 当用户操作完毕某个item并且其动画也结束后会调用该方法，用来恢复ItemView的初始状态，防止由于复用而产生的显示错乱问题。
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof IItemTouchHelperViewHolder) {
            ((IItemTouchHelperViewHolder) viewHolder).onItemClear();
        }
    }

    @Override
    public int getBoundingBoxMargin() {
        return super.getBoundingBoxMargin();
    }

    /**
     * swipe 到 距离的比例后，才触发侧滑 onSwiped 方法
     */
    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeThreshold(viewHolder);
//        return 0.2f;
    }

    /**
     * 针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
     */
    public float getSwipeVelocityThreshold(float defaultValue) {
        return super.getSwipeVelocityThreshold(defaultValue);
    }

    /**
     * 针对swipe状态，swipe的逃逸速度，换句话说就算没达到getSwipeThreshold设置的距离，达到了这个逃逸速度item也会被swipe消失掉
     */
    public float getSwipeEscapeVelocity(float defaultValue) {
        return super.getSwipeEscapeVelocity(defaultValue);
    }

    /**
     * 设置用户的手指离开后的动画持续时间 , 单位 毫秒 ms
     */
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }
}
