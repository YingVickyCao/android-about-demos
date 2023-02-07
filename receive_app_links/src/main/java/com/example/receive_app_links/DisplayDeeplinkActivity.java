package com.example.receive_app_links;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class DisplayDeeplinkActivity extends AppCompatActivity {
    private static final String TAG = DisplayDeeplinkActivity.class.getSimpleName();

    public static String HOST = "open.my.app";
    public static String SCHEMA_HTTP = "http";
    public static String SCHEMA_HTTPS = "https";
    public static String SCHEMA_APP = "app";
    public static String DEEP_LINK_PATH_PREFIX_MENU_BLOG = "menucode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_deeplink);

        Intent intent = getIntent();
        Toast.makeText(this, "Display this deep link", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: intent:" + intent.toString());
        Log.d(TAG, "onCreate: action:" + intent.getAction());
        Log.d(TAG, "onCreate: category:" + intent.getCategories());
        Log.d(TAG, "onCreate: schema:" + intent.getScheme());
        Log.d(TAG, "onCreate: data:" + intent.getData());

        parseLink(intent.getDataString());
    }

    private void parseLink(String link) {
        String data = null;
        if (null != link && link.toLowerCase().startsWith(SCHEMA_HTTP + "//" + HOST)) {
            // 此处简化了。
            // 实际开发中，
            // 1）、用的可能是一个bean，用来存储各种数据，比如video id，menu code，menu id,author id, author name.
            // 2）、可能不会直接在此处打开。Example ：global memory cache deeplink bean -> then send event to MainActivity -> MainActivity open it.
            // because MainActivity is always exist in the whole app lifecycle.
            data = parseVideo(link);
            if (null != data && !data.isEmpty()) {
                displayContent("This is a video ", "open \n" + link + "\nsuccessfully");
                return;
            }
            data = parseMenuBlog(link);
            if (null != data && !data.isEmpty()) {
                displayContent("This is a Blog Page ", "open \n" + link + "\nsuccessfully");
                return;
            }
        }

        data = parseJump(link);
        if (null != data && !data.isEmpty()) {
            displayContent("This is a menu or a article", "open \n" + link + "\nsuccessfully");
            return;
        }
        notSupport(link);
    }

    /**
     * @param link http://192.168.71.15:8080/video?videoId=V9763565
     *             =>scheme=http,host=192.168.71.15
     * @return video id. V9763565
     */
    @Nullable
    private String parseVideo(String link) {
        if (link.contains("videoId=")) {
            int index = link.indexOf("videoId=");
            return link.substring(index);
        }
        return null;
    }

    /**
     * @param link http://192.168.71.15:8080/menu/blog?menuId=9763565&menuCode=BLOG
     *             =>scheme=http,host=192.168.71.15
     * @return menu code. BLOG
     */
    @Nullable
    private String parseMenuBlog(String link) {
        if (link.contains(DEEP_LINK_PATH_PREFIX_MENU_BLOG)) {
            int index = link.indexOf("menuCode=");
            return link.substring(index);
        }
        return null;
    }

    // app://open.my.app?menucode=HotNew&articleId=N9647#Intent;scheme=app;package=com.example.receive_app_links;end
    private String parseJump(String link) {
        final String INTENT_RUN_TYPE = "app://open.my.app";
        int index = link.indexOf(INTENT_RUN_TYPE);
        if (index < 0) {
            return null;
        }
        String paramToParse = link.substring(index + INTENT_RUN_TYPE.length()); // menucode=HotNew&articleId=N9647
        String[] keyValueArray = paramToParse.split("&");
        Map<String, String> params = new HashMap<>();
        for (String item : keyValueArray) {
            String[] keyValue = item.split("=");
            if (null != keyValue && 2 == keyValue.length) {
                params.put(keyValue[0], keyValue[1]);
            }
        }

        String menuCodeValue = params.get("menucode"); // HotNew
        String articleIdValue = params.get("articleId");
        Log.d(TAG, "parseSplash: menucode=" + menuCodeValue + ",articleId=" + articleIdValue);
        return articleIdValue;
    }

    @Nullable
    private String parseQRScan() {
        // same as parseSplash
        return null;
    }

    private void displayContent(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void notSupport(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Not Support");
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}