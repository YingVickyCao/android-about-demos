package com.hades.example.android.widget.custom_view.mesh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class BitmapMeshView extends View {
    private static final String TAG = "BitmapMeshView";

    private final int WIDTH_MESH_NUM = 4;
    private final int HEIGHT_MESH_NUM = 4;

    private final int COUNT = (WIDTH_MESH_NUM + 1) * (HEIGHT_MESH_NUM * 1);
    private final float verts[] = new float[COUNT * 2];
    private final float orig[] = new float[COUNT * 2];


    Bitmap bitmap;

    public BitmapMeshView(Context context) {
        super(context);
        setFocusable(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_3);

        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        int index = 0;

        boolean flag = false;
        for (int y = 0; y <= HEIGHT_MESH_NUM; y++) {
            float fy = bitmapHeight * y / HEIGHT_MESH_NUM;
            for (int x = 0; x <= WIDTH_MESH_NUM; x++) {
                float fx = bitmapWidth * x / WIDTH_MESH_NUM;
                try {
                    if (index * 2 >= orig.length) {
                        flag = true;
                        break;
                    }
                    orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                    orig[index * 2 + 1] = verts[index * 2 + 1] = fy;
                    index += 1;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    Log.d(TAG, "BitmapMeshView: x=" + x + ",y=" + y);
                }
            }

            if (flag) {
                break;
            }
        }

        setBackgroundColor(Color.WHITE);
    }

    public BitmapMeshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BitmapMeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BitmapMeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmapMesh(bitmap, WIDTH_MESH_NUM, HEIGHT_MESH_NUM, verts, 0, null, 0, null);
    }

    private void wrap(float cx, float cy) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float dx = cx - orig[i + 0];
            float dy = cy - orig[i + 1];
            float dd = dx * dx + dy * dy;
            float d = (float) Math.sqrt(dd);
            float pull = 80000 / (dd * d);

            if (pull >= 1) {
                verts[i + 0] = cx;
                verts[i + 1] = cy;
            } else {
                verts[i + 0] = orig[i + 0] + dx * pull;
                verts[i + 1] = orig[i + 1] + dy * pull;
            }
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        wrap(event.getX(), event.getY());
        return true;
    }
}
