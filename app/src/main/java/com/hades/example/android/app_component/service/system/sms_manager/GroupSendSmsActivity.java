package com.hades.example.android.app_component.service.system.sms_manager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

import java.util.ArrayList;

/*
<!-- 授予读联系人ContentProvider的权限 -->
	<uses-permission android:name="android.permission.READ_CONTACTS"/>
	<!-- 授予发送短信的权限 -->
	<uses-permission android:name="android.permission.SEND_SMS"/>
 */
public class GroupSendSmsActivity extends BaseActivity {
    EditText numbers, content;
    private View mRoot;
    ArrayList<String> toSendPhoneList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_system_sms_group_send_sms);

        mRoot = findViewById(R.id.root);

        numbers = findViewById(R.id.numbers);
        content = findViewById(R.id.content);

        findViewById(R.id.select).setOnClickListener(v -> selectContacts());
        findViewById(R.id.sendBroadcast).setOnClickListener(v -> groupSendSms());

        requestPermission();
    }

    protected void requestPermission() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS}, new OnPermissionResultCallback() {

            @Override
            public void onPermissionGranted() {
                Toast.makeText(GroupSendSmsActivity.this, "READ_CONTACTS and SEND_SMS granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(GroupSendSmsActivity.this, "READ_CONTACTS and SEND_SMS denied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionError(String message) {

            }

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(mRoot, "Request permission for group send sms", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }
        });
    }

    private void selectContacts() {
        // 查询联系人的电话号码
        final Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return cursor.getCount();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                cursor.moveToPosition(position);
                CheckBox rb = new CheckBox(GroupSendSmsActivity.this);
                // 获取联系人的电话号码，并去掉中间的中画线、空格
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace("-", "").replace(" ", "");
                rb.setText(number);
                // 如果该号码已经被加入发送人名单，默认勾选该号码
                if (isChecked(number)) {
                    rb.setChecked(true);
                }
                return rb;
            }
        };

        View selectView = getLayoutInflater().inflate(R.layout.service_system_sms_group_send_sms_4_select_contacts, null);
        final ListView listView = selectView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        new AlertDialog.Builder(GroupSendSmsActivity.this)
                .setView(selectView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetSelectContactsResult();
                        for (int i = 0; i < listView.getCount(); i++) {
                            // TODO: 23/07/2018 (CheckBox) listView.getChildAt(i)
                            CheckBox checkBox = (CheckBox) listView.getChildAt(i);
                            if (checkBox.isChecked()) {
                                buildSelectContactsResult(checkBox.getText().toString());
                            }
                        }
                        numbers.setText(toSendPhoneList.toString());
                    }
                }).show();
    }

    private void resetSelectContactsResult() {
        toSendPhoneList.clear();
    }

    private void buildSelectContactsResult(String phone) {
        toSendPhoneList.add(phone);
    }

    private void groupSendSms() {
        if (null == content.getText()) {
            Toast.makeText(this, "Please input sms msg", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: 23/07/2018 主线程循环发送邮件，若多人数很多且网络延迟，群发短信可能变成一个耗时任务。 -> IntentService中发送短信，群发短信完成后，通过广播通知前台 Activity。
        for (String number : toSendPhoneList) {
            PendingIntent pi = PendingIntent.getActivity(GroupSendSmsActivity.this, 0, new Intent(), 0);
            getSmsManager().sendTextMessage(number, null, content.getText().toString(), pi, null);
        }
        Toast.makeText(GroupSendSmsActivity.this, "短信群发完成", Toast.LENGTH_SHORT).show();
    }

    private SmsManager getSmsManager() {
        return SmsManager.getDefault();
    }

    // 判断某个电话号码是否已在群发范围内
    public boolean isChecked(String phone) {
        for (String s1 : toSendPhoneList) {
            if (s1.equals(phone)) {
                return true;
            }
        }
        return false;
    }

}