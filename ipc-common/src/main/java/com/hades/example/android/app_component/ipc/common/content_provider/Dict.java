package com.hades.example.android.app_component.ipc.common.content_provider;

import android.net.Uri;
import android.provider.BaseColumns;

// https://developer.android.google.cn/guide/topics/providers/content-provider-creating?hl=zh-cn
public final class Dict {
    public final static String SCHEME = "content";
    public static final String AUTHORITY = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider";
    public static final int CODE_WORDS_URI = 1; // 用于标识不同URI的常量
    public static final int CODE_WORD_URI = 2; // 用于标识不同URI的常量
    public final static String PATH_WORDS_URI = "words";
    public final static String PATH_WORD_URI = "word/#"; // # 表示任意数字
    public final static String PATH_WORD_URI_2 = "word";

    // 操作的数据是多项记录
    // content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/words
    // 定义该Content提供服务的两个Uri
    // 写法1
    public final static Uri WORDS_URI = Uri.parse(SCHEME + "://" + AUTHORITY + "/" + PATH_WORDS_URI);

    // 写法2
    public static Uri getUri() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .path(PATH_WORDS_URI)
                .build();
    }

    // 操作的数据是单项记录
    // content://<authority>/<path>/<row_id>,row_id ： 每条记录都有一个 行 ID (Row ID)，（这是 ContentProvider 的一种约定）
    // content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/word/rowId
    // 写法1
    public final static Uri WORD_URI = Uri.parse(SCHEME + "://" + AUTHORITY + "/" + PATH_WORD_URI);

    // 写法2
    public static Uri getUriById(long id) {
        return getUriById(id);
    }

    public static Uri getUriById(String id) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .path(PATH_WORD_URI_2)
                .appendPath(id)
                .build();
    }


    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class DictColumns implements BaseColumns {
        // 定义Content所允许操作的三个数据列
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
    }
}