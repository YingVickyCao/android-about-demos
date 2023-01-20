package com.hades.example.android.deeplink;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class DeeplinkActivity extends AppCompatActivity {
    private static final String TAG = "DeeplinkActivity";

    /**
     * Available on:local server
     * http://127.0.0.1:8080
     * http://192.168.71.15:8080
     * http://192.168.71.20:8080
     */
    public static String HOST = "192.168.71.15";
    public static String SCHEMA_HTTP = "http";
    public static String SCHEMA_HTTPS = "https";
    public static String DEEP_LINK_PATH_PREFIX_MENU_BLOG = "menu/blog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);

        // ATTENTION: This was auto-generated to handle app links.
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        // http://192.168.71.15:8080/menu/blog?menucode=blog&geocode=123456&id=BLOG9763565
        // =>
        // scheme=http
        // host=192.168.71.15

        //  "deeplinkdemo://go?menucode=blog#Intent;scheme=deeplinkdemo;package=com.hades.example.android.deeplink;end"
        Log.d(TAG, "onCreate: " + intent);
        Log.d(TAG, "onCreate: ,uri:" + data.toString());

        /**
         * For Splash Page :
         * test case 1 :
         * Splash Page use this to invoke app:  "intent://go?project=img#Intent;scheme=deeplinkdemo;package=com.hades.example.android.deeplink;end"
         * DeeplinkActivity received Intent : Intent { act=android.intent.action.VIEW cat=[android.intent.category.BROWSABLE] dat=deeplinkdemo://go?project=img flg=0x14400000 pkg=com.hades.example.android.deeplink cmp=com.hades.example.android.deeplink/.DeeplinkActivity (has extras) }
         * Data from Intent.data : deeplinkdemo://go?project=img
         * => scheme = "deeplinkdemo"
         * => host = "NOT CACHED"
         */
        parseLink(intent.getDataString());
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
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

        data = parseSplash(link);
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

    // Splash Page call this to invoke app: "deeplinkdemo://go?menucode=HotNew&articleId=N9647;#Intent;scheme=deeplinkdemo;package=com.hades.example.android.deeplink;end"
    // Intent received:
    // Received text from Intent: deeplinkdemo://go?menucode=HotNew&articleId=N9647
    private String parseSplash(String link) {
        final String INTENT_RUN_TYPE = "deeplinkdemo://go";
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