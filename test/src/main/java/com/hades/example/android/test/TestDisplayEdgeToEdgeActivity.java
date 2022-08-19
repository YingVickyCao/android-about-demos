package com.hades.example.android.test;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * https://developer.android.google.cn/training/gestures/edge-to-edge
 */
public class TestDisplayEdgeToEdgeActivity extends AppCompatActivity {
    private static final String TAG = "TestDisplayEdgeToEdgeActivity";

    View floatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Step 1: Lay out your app in full screen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // Step 2: Change the color of the system bars
        // way 2
        WindowInsetsControllerCompat windowInsetsControllerCompat = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (null != windowInsetsControllerCompat) {
            windowInsetsControllerCompat.setAppearanceLightStatusBars(true);
//            windowInsetsControllerCompat.setAppearanceLightNavigationBars(true);
        }

        setTheme(R.style.AppTheme_DisplayEdgeToEdge);
        setContentView(R.layout.activity_display_edge_to_edge);

        floatBtn = findViewById(R.id.floatBtn);

        /**
         * Step 3: Handle overlaps using insets
         */
        // System bars insets
        ViewCompat.setOnApplyWindowInsetsListener(floatBtn, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            v.setLayoutParams(mlp);

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });

        // System gesture insets
        // Use these insets to move or pad swipeable views away from the edges. Common use cases include bottom sheets,
        // swiping in games, and carousels implemented using ViewPager.
        ViewCompat.setOnApplyWindowInsetsListener(floatBtn, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures());
            // Apply the insets as padding to the view. Here we're setting all of the
            // dimensions, but apply as appropriate to your layout. You could also
            // update the views margin if more appropriate.
            floatBtn.setPadding(insets.left, insets.top, insets.right, insets.bottom);

            // Return CONSUMED if we don't want the window insets to keep being passed
            // down to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });

        // Step 4: Immersive mode
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            // Hide the system bars.
//            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            // Show the system bars.
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars());
        }
    }
}