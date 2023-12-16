package com.hades.example.android.widget.custom_view;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.tools.permission.IRationaleOnClickListener;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;
import com.hades.example.android.widget.custom_view.Xfermode.XfermodeFragment;
import com.hades.example.android.widget.custom_view.cascadelayout.CascadeLayoutActivity;
import com.hades.example.android.widget.custom_view.drawCircle.ball.FingerMovedBallFragment;
import com.hades.example.android.widget.custom_view.drawText.TestDrawTextFragment;
import com.hades.example.android.widget.custom_view.drawing_board.DrawingBoardFragment;
import com.hades.example.android.widget.custom_view.layer.CanvasLayerExampleFragment;
import com.hades.example.android.widget.custom_view.matrix.MatrixOnBitmapFragment;
import com.hades.example.android.widget.custom_view.mesh.BitmapMeshFragment;
import com.hades.example.android.widget.custom_view.path_effect.TestPathEffectFragment;
import com.hades.example.android.widget.custom_view.shader.ShaderFragment;
import com.hades.example.android.widget.custom_view.shadow.TestShadowViewFragment;

/**
 * https://www.cnblogs.com/andriod-html5/archive/2012/04/30/2539419.html
 */
public class CustomViewActivity extends BaseActivity {
    private static final String TAG = CustomViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_custom_view_layout);
        Log.d(TAG, "onCreate: ");
        initViews(R.id.root);
        findViewById(R.id.page_drawText).setOnClickListener(v -> page_drawText());
        findViewById(R.id.page_Canvas_and_Paint).setOnClickListener(v -> page_Canvas_and_Paint());
        findViewById(R.id.page_PathEffect).setOnClickListener(v -> page_PathEffect());
        findViewById(R.id.page_DrawingBoard).setOnClickListener(v -> page_DrawingBoard());
        findViewById(R.id.page_FingerMovedBall).setOnClickListener(v -> page_FingerMovedBall());
        findViewById(R.id.page_MatrixOnBitmap).setOnClickListener(v -> page_MatrixOnBitmap());
        findViewById(R.id.page_BitmapMesh).setOnClickListener(v -> page_BitmapMesh());
        findViewById(R.id.page_Shader).setOnClickListener(v -> page_Shader());
        findViewById(R.id.page_CascadeLayout).setOnClickListener(v -> page_CascadeLayout());
        findViewById(R.id.page_Shadow_and_ShadowLayer).setOnClickListener(v -> page_Shadow_and_ShadowLayer());
        findViewById(R.id.page_Layer).setOnClickListener(v -> page_Layer());
        findViewById(R.id.page_Xfermode).setOnClickListener(v -> page_Xfermode());
        findViewById(R.id.page_red_badge).setOnClickListener(v -> page_red_badge());

        requestPermission();
    }

    private void requestPermission() {
        // FIXED_ERROR:java.io.FileNotFoundException: /sdcard/bg004.JPG: open failed: EACCES (Permission denied)
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void showRationaleContextUI(IRationaleOnClickListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD Card permission", Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.ok), view -> callback.clickOK()).setAction(getString(R.string.cancel), view -> callback.clickCancel()).show();
            }

            @Override
            public void granted() {
                Toast.makeText(CustomViewActivity.this, "SD Card permission granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        page_red_badge();
    }

    private void page_drawText() {
        showFragment(new TestDrawTextFragment());
    }

    private void page_Canvas_and_Paint() {
        showFragment(new TestCanvasPaintFragment());
    }

    private void page_PathEffect() {
        showFragment(new TestPathEffectFragment());
    }

    private void page_DrawingBoard() {
        showFragment(new DrawingBoardFragment());
    }

    private void page_FingerMovedBall() {
        showFragment(new FingerMovedBallFragment());
    }

    private void page_MatrixOnBitmap() {
        showFragment(new MatrixOnBitmapFragment());
    }

    private void page_BitmapMesh() {
        showFragment(new BitmapMeshFragment());
    }

    private void page_Shader() {
        showFragment(new ShaderFragment());
    }

    private void page_CascadeLayout() {
        showActivity(CascadeLayoutActivity.class);
    }

    private void page_Shadow_and_ShadowLayer() {
        showFragment(new TestShadowViewFragment());
    }

    private void page_Layer() {
        showFragment(new CanvasLayerExampleFragment());
    }

    private void page_Xfermode() {
        showFragment(new XfermodeFragment());
    }

    private void page_red_badge() {
        showFragment(new RedBadgeFragment());
    }
}