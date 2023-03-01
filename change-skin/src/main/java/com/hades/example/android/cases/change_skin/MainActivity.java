package com.hades.example.android.cases.change_skin;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 如何实现应用内换肤？
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private String mSkinPluginPath = Environment.getExternalStorageDirectory() + File.separator + "skin-plugin-debug.apk";
    private String mSkinPluginPackage = "com.hades.example.android.cases.change_skin.skin_plugin";

    TextView mTextView;
    ImageView mImageView;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        mImageView = findViewById(R.id.imageView);
        mButton = findViewById(R.id.btn);

        findViewById(R.id.change_skin).setOnClickListener(v -> changeSkin());
        findViewById(R.id.reset_skin).setOnClickListener(v -> resetSkin());
    }

    private void changeSkin() {
        loadPlugin(mSkinPluginPath, mSkinPluginPackage);
    }

    private void loadPlugin(String skinPluginPath, String skinPluginPackage) {
        try {
            // AssetManager.addAssetPath();
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, skinPluginPath);

            Resources superResources = getResources();
            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(), superResources.getConfiguration());

            ResourcesManager resourcesManager = new ResourcesManager(resources, mSkinPluginPackage);
            Drawable drawable = resourcesManager.getDrawableByResourceName("lightbulb");
            if (null != drawable) {
                mImageView.setImageDrawable(drawable);
            }

        } catch (Exception ex) {
            Log.d(TAG, "loadPlugin: ");
        }

    }

    private void resetSkin() {

    }
}