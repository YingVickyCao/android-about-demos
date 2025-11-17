package com.hades.example.android.app_component.content_provider.dict.common;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * 用来访问app中的 DictContentProvider
 */
public abstract class DictBasicActivity extends Activity {
    private static final String TAG = "DictBasicActivity";

    public static final String KEY_SEARCH_RESULT = "search_result";

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

        initView();
        findViewById(R.id.insert).setOnClickListener(v -> insert());
        findViewById(R.id.query).setOnClickListener(v -> query());
        findViewById(R.id.queryById).setOnClickListener(v -> queryById());

        init();
    }

    public abstract void init();

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

    protected void insert() {
        String word = ((EditText) findViewById(R.id.word)).getText().toString();
        if (word.isEmpty()) {
            Toast.makeText(this, "Input invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        String detail = buildDetail(word);
        boolean isSuccess = doInsert(word, detail);
        Log.e(TAG, "insert: " + isSuccess);

        if (isSuccess) {
            Toast.makeText(this, "添加生词成功！", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract boolean doInsert(String word, String detail);

    private void delete(long id) {
        boolean isSuccess = doDelete(id);
        if (isSuccess) {
            Toast.makeText(this, "Delete 生词成功！", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract boolean doDelete(long id);

    private void update(String word, long id) {
        showUpdateWordDialog(word, new DictInputListener() {
            @Override
            public void onInput(String enteredText) {
                String detail = buildDetail(enteredText);
                boolean isSuccess = doUpdate(enteredText, detail, id);
                if (isSuccess) {
                    Toast.makeText(DictBasicActivity.this, "Update 生词成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public abstract boolean doUpdate(String word, String detail, long id);

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
        String keyword = ((EditText) findViewById(R.id.key)).getText().toString();

        Cursor cursor = doQuery(keyword);
        List<DictRowBean> result = convertCursorToList(cursor);

        list.clear();
        list.addAll(result);
        adapter.notifyDataSetChanged();

        if (null != cursor) {
            cursor.close();
        }
    }

    private void queryById() {
        String keyword = ((EditText) findViewById(R.id.dicId)).getText().toString();

        Cursor cursor = doQueryById(keyword);
        List<DictRowBean> result = convertCursorToList(cursor);

        list.clear();
        list.addAll(result);
        adapter.notifyDataSetChanged();

        if (null != cursor) {
            cursor.close();
        }
    }

    public abstract Cursor doQuery(String keyword);

    public abstract Cursor doQueryById(String id);

    protected List<DictRowBean> convertCursorToList(Cursor cursor) {
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