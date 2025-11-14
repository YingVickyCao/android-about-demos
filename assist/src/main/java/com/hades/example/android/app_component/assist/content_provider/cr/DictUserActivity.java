package com.hades.example.android.app_component.assist.content_provider.cr;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hades.example.android.app_component.assist.R;
import com.hades.example.android.app_component.content_provider.dict.Dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 用来访问app中的 DictContentProvider
 */
public class DictUserActivity extends Activity {
    private static final String TAG = "DictUserActivity";

    private ContentResolver contentResolver;
    public static final String KEY_SEARCH_RESULT = "search_result";
    private DictContentObserver mDictContentObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_dict);

        ((TextView) findViewById(R.id.topic)).setText("Dict Content Resolver Operations");

        contentResolver = getContentResolver();

        /**
         * java.lang.SecurityException: Failed to find provider ** for user 0; expected to find a valid ContentProvider for this authority
         */
        mDictContentObserver = new DictContentObserver(this, new Handler(Looper.getMainLooper()));
        getContentResolver().registerContentObserver(Dict.getUri(), true, mDictContentObserver);
        getContentResolver().notifyChange(Dict.getUri(), mDictContentObserver);

        findViewById(R.id.insert).setOnClickListener(v -> insert());
        findViewById(R.id.query).setOnClickListener(v -> query());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != mDictContentObserver) {
            getContentResolver().unregisterContentObserver(mDictContentObserver);
            mDictContentObserver = null;
        }
    }

    private void insert() {
        String word = ((EditText) findViewById(R.id.word)).getText().toString();

        if (word.isEmpty()) {
            Toast.makeText(DictUserActivity.this, "Input invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        String detail = buildDetail(word);

        doInsertWords(word, detail);

        Toast.makeText(DictUserActivity.this, "添加生词成功！", Toast.LENGTH_SHORT).show();
    }


    private String buildDetail(String word) {
        return word + "  detail";
    }

    private void doInsertWords(String word, String detail) {
        ContentValues values = new ContentValues();
        values.put(Dict.Word.WORD, word);
        values.put(Dict.Word.DETAIL, detail);
        Uri uri = contentResolver.insert(Dict.WORDS_URI, values);
        Log.e(TAG, "doInsertWords: " + uri);
    }

    private void query() {
        String key = ((EditText) findViewById(R.id.key)).getText().toString();

        Cursor cursor = doSearchWords(key);

        // 创建一个Bundle对象
        Bundle data = new Bundle();
        data.putSerializable(KEY_SEARCH_RESULT, convertCursorToList(cursor));
        Intent intent = new Intent(DictUserActivity.this, DictUserSearchResultActivity.class);
        intent.putExtras(data);
        startActivity(intent);
    }

    private Cursor doSearchWords(String key) {
        return contentResolver.query(Dict.WORDS_URI, null, "word like ? or detail like ?", new String[]{"%" + key + "%", "%" + key + "%"}, null);
    }

    private ArrayList<Map<String, String>> convertCursorToList(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        if (null == cursor) {
            return result;
        }
        // 遍历Cursor结果集
        while (cursor.moveToNext()) {
            // 将结果集中的数据存入ArrayList中
            Map<String, String> map = new HashMap<>();
            // 取出查询记录中第2列、第3列的值
            map.put(Dict.Word.WORD, cursor.getString(1));
            map.put(Dict.Word.DETAIL, cursor.getString(2));
            result.add(map);
        }
        return result;
    }
}