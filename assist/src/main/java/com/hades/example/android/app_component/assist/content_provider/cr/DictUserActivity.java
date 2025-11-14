package com.hades.example.android.app_component.assist.content_provider.cr;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.hades.example.android.app_component.assist.R;
import com.hades.example.android.app_component.content_provider.dict.common.DicListener;
import com.hades.example.android.app_component.content_provider.dict.common.Dict;
import com.hades.example.android.app_component.content_provider.dict.common.DictAdapter;
import com.hades.example.android.app_component.content_provider.dict.common.DictInputListener;
import com.hades.example.android.app_component.content_provider.dict.common.DictRowBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 用来访问app中的 DictContentProvider
 */
public class DictUserActivity extends Activity {
    private static final String TAG = "DictUserActivity";

    private ContentResolver contentResolver;
    public static final String KEY_SEARCH_RESULT = "search_result";
    private DictContentObserver mDictContentObserver;
    private ListView listView;
    private List<DictRowBean> list;
    private DictAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_dict);

        ((TextView) findViewById(R.id.topic)).setText("Dict Content Resolver Operations");
        listView = findViewById(R.id.dictList);
        list = new ArrayList<>();

        contentResolver = getContentResolver();

        /**
         * FIX_ERROR:java.lang.SecurityException: Failed to find provider ** for user 0; expected to find a valid ContentProvider for this authority
         * https://blog.csdn.net/qq_41806128/article/details/115190130
         *
         * 解决：
         * 1 提供 content provider 的app 中，添加 android:authorities 并  android:exported
         *  <provider
         *             android:name=".app_component.content_provider.dict.DictContentProvider"
         *             android:authorities="com.hades.example.android.app_component.content_provider.dict.DictContentProvider"
         *             android:enabled="true"
         *             android:exported="true" />
         * 使用content provider 的assist 中, UR 必须 提供正确，它的authority 必须对应 android:authorities
         * 2 使用content provider 的assist 中,要声明 queries
         *
         *    <queries>
         *         <provider android:authorities="com.hades.example.android.app_component.content_provider.dict.DictContentProvider" />
         *     </queries>
         */
        mDictContentObserver = new DictContentObserver(this, new Handler(Looper.getMainLooper()));
        getContentResolver().registerContentObserver(Dict.getUri(), true, mDictContentObserver);
        getContentResolver().notifyChange(Dict.getUri(), mDictContentObserver);

        initView();
        findViewById(R.id.insert).setOnClickListener(v -> insert());
        findViewById(R.id.query).setOnClickListener(v -> query());
    }

    private void initView() {
        DicListener listener = new DicListener() {
            @Override
            public void onUpdate(int position, String word, long key) {
                update(word, key);
            }

            @Override
            public void onDelete(int position, long key) {
                delete(key);
            }
        };
        adapter = new DictAdapter(list, listener, this);
        listView.setAdapter(adapter);
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

        ContentValues values = new ContentValues();
        values.put(Dict.Word.WORD, word);
        values.put(Dict.Word.DETAIL, detail);
        Uri uri = contentResolver.insert(Dict.WORDS_URI, values);
        Log.e(TAG, "insert: " + uri);

        if (null != uri) {
            Toast.makeText(DictUserActivity.this, "添加生词成功！", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete(long id) {
        ContentValues values = new ContentValues();
        values.put(Dict.Word._ID, id);
        String where = Dict.Word._ID + " = ?";
        int rowNum = contentResolver.delete(Dict.WORDS_URI, where, new String[]{String.valueOf(id)});
        Log.e(TAG, "doInsertWords: " + rowNum);
        if (rowNum > 0) {
            Toast.makeText(DictUserActivity.this, "Delete 生词成功！", Toast.LENGTH_SHORT).show();
        }
    }

    private void update(String word, long id) {
        showUpdateWordDialog(word, new DictInputListener() {
            @Override
            public void onInput(String enteredText) {
                ContentValues values = new ContentValues();
                values.put(Dict.Word.WORD, enteredText);
                values.put(Dict.Word.DETAIL, buildDetail(enteredText));
                String where = Dict.Word._ID + " = ?";
                int rowNumer = contentResolver.update(Dict.WORDS_URI, values, where, new String[]{String.valueOf(id)});
                Log.e(TAG, "update: " + rowNumer);
            }
        });
    }

    private void showUpdateWordDialog(String word, DictInputListener listener) {
        final EditText input = new EditText(this);
        input.setText(word);
        input.setHint("请输入内容..."); // 设置提示文字
        // input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // 如果需要密码输入
        // input.setText("默认值"); // 设置默认值

        // 创建 AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("输入框对话框") // 对话框标题
                .setView(input) // 设置 EditText 为对话框的视图
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enteredText = input.getText().toString();
                        listener.onInput(enteredText);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show(); // 显示对话框
    }

    private String buildDetail(String word) {
        return "detail of " + word;
    }

    private void query() {
        String key = ((EditText) findViewById(R.id.key)).getText().toString();
        Cursor cursor = contentResolver.query(Dict.WORDS_URI, null, "word like ? or detail like ?", new String[]{"%" + key + "%", "%" + key + "%"}, null);
        List<DictRowBean> result = convertCursorToList(cursor);
        list.clear();
        list.addAll(result);
        adapter.notifyDataSetChanged();
    }

    private List<DictRowBean> convertCursorToList(Cursor cursor) {
        List<DictRowBean> result = new ArrayList<>();
        if (null == cursor) {
            return result;
        }
        // 遍历Cursor结果集
        while (cursor.moveToNext()) {
            // 将结果集中的数据存入ArrayList中
            DictRowBean row = new DictRowBean(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            result.add(row);
        }
        return result;
    }
}