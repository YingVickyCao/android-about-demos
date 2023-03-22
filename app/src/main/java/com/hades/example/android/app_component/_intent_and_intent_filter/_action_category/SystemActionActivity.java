package com.hades.example.android.app_component._intent_and_intent_filter._action_category;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.tools.permission.IRationaleOnClickListener;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;

public class SystemActionActivity extends AppCompatActivity {
    final int REQUEST_CODE_PICK_CONTACT = 1000;

    private TextView contactName;
    private TextView phoneNum;
    private View mRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_action_system);
        mRoot = findViewById(R.id.root);

        contactName = findViewById(R.id.contactName);
        phoneNum = findViewById(R.id.contactPhone);

        findViewById(R.id.actionGetContent).setOnClickListener(v -> actionGetContent());
        findViewById(R.id.backToSystemHome).setOnClickListener(v -> backToSystemHome());
        requestPermission();
    }

    private void requestPermission() {
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void showRationaleContextUI(IRationaleOnClickListener callback) {
                Snackbar.make(mRoot, "Request permission for READ_CONTACTS", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.clickOK())
                        .setAction(getString(R.string.cancel), view -> callback.clickCancel())
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(SystemActionActivity.this, "READ_CONTACTS granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_CONTACTS);
    }

    private void actionGetContent() {
//         <uses-permission android:name="android.permission.READ_CONTACTS" />
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
        startActivityForResult(intent, REQUEST_CODE_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            handlePhoneData(data);
        }
    }

    private void handlePhoneData(Intent data) {
        Uri contactData = data.getData();
        if (null == contactData) {
            return;
        }
        CursorLoader cursorLoader = new CursorLoader(SystemActionActivity.this, contactData, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        if (null == cursor) {
            return;
        }

        new Thread(() -> {
            if (cursor.moveToFirst()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                String phoneNumTemp = "此联系人暂未输入电话号码";
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                if (null != phones) {
                    if (phones.moveToFirst()) {
                        phoneNumTemp = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }

                final String phoneNum = phoneNumTemp;

                runOnUiThread(() -> {
                    SystemActionActivity.this.contactName.setText(contactName);
                    SystemActionActivity.this.phoneNum.setText(phoneNum);
                });
            }

            cursor.close();
        }).start();
    }

    private void backToSystemHome() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}