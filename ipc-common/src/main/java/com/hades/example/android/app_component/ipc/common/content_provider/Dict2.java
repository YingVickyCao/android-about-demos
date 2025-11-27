package com.hades.example.android.app_component.ipc.common.content_provider;

import android.net.Uri;
import android.provider.BaseColumns;

// https://developer.android.google.cn/guide/topics/providers/content-provider-creating?hl=zh-cn
public final class Dict2 {
    public final static String SCHEME = "content";
    public static final String AUTHORITY = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider2";
    public static final int CODE_WORDS_URI = 1; // 用于标识不同URI的常量
    public final static String PATH_WORDS_URI = "words";

    public static String FILE_NAME = "Dict2.xml";

    public static String readPermission = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider2.readPermission";
    public static String writePermission = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider2.writePermission";

    // 操作的数据是多项记录
    // content://com.hades.example.android.app_component.content_provider.dict2.DictContentProvider2/words
    // 定义该Content提供服务的两个Uri
    // 写法1
    public final static Uri WORDS_URI = Uri.parse(SCHEME + "://" + AUTHORITY + "/" + PATH_WORDS_URI);
    public static Uri buildURI(){
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .path(PATH_WORDS_URI)
                .build();
    }

    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class DictColumns implements BaseColumns {
    }
}