package com.hades.example.android.widget.custom_view.mesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

public class BitmapMeshView extends View {
    private static final String TAG = "BitmapMeshView";

    // 将图在水平和垂直方向上划分格
    private final int MESH_WIDTH = 2;
    private final int MESH_HEIGHT = 2;

    // 记录图片包含的点
    private final int COUNT = (MESH_WIDTH + 1) * (MESH_HEIGHT + 1);

    // 扭曲前，点的XY坐标
    // originVert[0] 和 originVert [1] ，表示第一个点的 x 坐标和 y 坐标
    private final float[] originVert = new float[COUNT * 2];
    // 扭曲后，点的XY坐标
    private final float[] meshedVert = new float[COUNT * 2];

    private Bitmap bitmap;

    public BitmapMeshView(Context context, int drawableId) {
        super(context);

        //根据指定资源加载图片
        bitmap = BitmapFactory.decodeResource(getResources(), drawableId);

        //获取图片宽度、高度
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();

        // 初始化原始和扭曲后的坐标
        int index = 0;
        for (int y = 0; y <= MESH_HEIGHT; y++) {
            float fy = bitmapHeight * y / MESH_HEIGHT;
            for (int x = 0; x <= MESH_WIDTH; x++) {
                float fx = bitmapWidth * x / MESH_WIDTH;
                originVert[index * 2] = meshedVert[index * 2] = fx;
                originVert[index * 2 + 1] = meshedVert[index * 2 + 1] = fy;
                index += 1;
            }
        }
        //设置背景色
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 对bitmap按 meshedVert 数组进行扭曲。从第一个点（由第5个参数0控制）开始扭曲
        canvas.drawBitmapMesh(bitmap, MESH_WIDTH, MESH_HEIGHT, meshedVert, 0, null, 0, null);
    }

    //工具方法，用于根据触摸事件的位置计算 originVert 数组里各元素的值

    /**
     * 获得触摸点的 x,y 坐标，拿这个值去减对应点的 x,y 值，计算出触摸点和每个坐标点的距离然后计算所谓的扭曲度：`80000 / ((float) (dd * d))`
     * 扭曲度 `>= 1` 的，直接让该坐标点指向这个触摸点;
     * 扭曲度`< 1` 的，则让各个顶点向触摸点发生偏移.
     * 然后再调用 `invalidate()` 重绘
     */
    private void warp(float cx, float cy) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float dx = cx - originVert[i];
            float dy = cy - originVert[i + 1];
            float dd = dx * dx + dy * dy;
            //计算每个坐标点与当前点（cx、cy）之间的距离
            float d = (float) Math.sqrt(dd);
            //计算扭曲度，距离当前点（cx、cy）越远，扭曲度越小
            float pull = 80000 / (dd * d);
            //保存bitmap上点经过扭曲后的座标
            if (pull >= 1) {
                meshedVert[i] = cx;
                meshedVert[i + 1] = cy;
            } else {
                //控制各顶点向触摸事件发生点偏移
                meshedVert[i] = originVert[i] + dx * pull;
                meshedVert[i + 1] = originVert[i + 1] + dy * pull;
            }
        }
        //通知View组件重绘
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //调用warp方法根据触摸屏事件的座标点来扭曲verts数组
        warp(event.getX(), event.getY());
        return true;
    }
}
