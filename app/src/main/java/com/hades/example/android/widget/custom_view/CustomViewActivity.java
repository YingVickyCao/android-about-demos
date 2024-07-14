package com.hades.example.android.widget.custom_view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.base.ViewsVisibilityHelper;
import com.hades.example.android.tools.FragmentUtils;
import com.hades.example.android.widget.custom_view.Xfermode.XfermodeFragment;
import com.hades.example.android.widget.custom_view.badge.RedBadgeFragment;
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
public class CustomViewActivity extends AppCompatActivity {
    private static final String TAG = CustomViewActivity.class.getSimpleName();

    ViewsVisibilityHelper visibilityHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_custom_view_layout);
        Log.d(TAG, "onCreate: ");
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

        if (null == visibilityHelper) {
            visibilityHelper = new ViewsVisibilityHelper(findViewById(R.id.topic), findViewById(R.id.scrollView), findViewById(R.id.fragmentRoot));
        }
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (visibilityHelper.isBtnsHiden()) {
                    FragmentUtils.remove(CustomViewActivity.this, R.id.fragmentRoot);
                    visibilityHelper.showBtns();
                } else {
                    finish();
                }
            }
        });
    }

    private void page_drawText() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestDrawTextFragment(), TestDrawTextFragment.class.getSimpleName());
    }

    private void page_Canvas_and_Paint() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestCanvasPaintFragment(), TestCanvasPaintFragment.class.getSimpleName());
    }

    private void page_PathEffect() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestPathEffectFragment(), TestPathEffectFragment.class.getSimpleName());
    }

    private void page_DrawingBoard() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new DrawingBoardFragment(), DrawingBoardFragment.class.getSimpleName());
    }

    private void page_FingerMovedBall() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new FingerMovedBallFragment(), FingerMovedBallFragment.class.getSimpleName());
    }

    private void page_MatrixOnBitmap() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new MatrixOnBitmapFragment(), MatrixOnBitmapFragment.class.getSimpleName());
    }

    private void page_BitmapMesh() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new BitmapMeshFragment(), BitmapMeshFragment.class.getSimpleName());
    }

    private void page_Shader() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new ShaderFragment(), ShaderFragment.class.getSimpleName());
    }

    private void page_CascadeLayout() {
        startActivity(new Intent(this, CascadeLayoutActivity.class));
    }

    private void page_Shadow_and_ShadowLayer() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new TestShadowViewFragment(), TestShadowViewFragment.class.getSimpleName());
    }

    private void page_Layer() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new CanvasLayerExampleFragment(), CanvasLayerExampleFragment.class.getSimpleName());
    }

    private void page_Xfermode() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new XfermodeFragment(), XfermodeFragment.class.getSimpleName());
    }

    private void page_red_badge() {
        visibilityHelper.hideBtns();
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, new RedBadgeFragment(), RedBadgeFragment.class.getSimpleName());
    }
}