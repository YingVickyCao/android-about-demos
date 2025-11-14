package com.hades.example.android.app_component.content_provider.dict;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Dict {
    // 定义该ContentProvider的Authority
    public static final String AUTHORITY = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider";

    // 用于标识不同URI的常量
    public static final int WORDS_URI_CODE = 1;
    public static final int WORD_URI_CODE = 2;

    public final static String URI_SCHEME = "content";

    public final static String WORDS_URI_PATH = "words";
    public final static String WORD_URI_PATH = "word/#";

    // 操作的数据是多项记录
    // content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/words
    // 定义该Content提供服务的两个Uri
    // 写法1
    public final static Uri WORDS_URI = Uri.parse(URI_SCHEME + "://" + AUTHORITY + "/" + WORDS_URI_PATH);

    // 写法2
    public static Uri getUri() {
        return new Uri.Builder()
                .scheme(URI_SCHEME)
                .authority(AUTHORITY)
                .path(WORDS_URI_PATH)
                .build();
    }

    // TODO: 2025/11/14
    // 操作的数据是单项记录
    // content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/word/1， 1 是 _id, 也就是key
    // 写法1
    public final static Uri WORD_URI = Uri.parse(URI_SCHEME + "://" + AUTHORITY + "/" + WORD_URI_PATH);

    // 写法2
    public static Uri getUriById() {
        return new Uri.Builder()
                .scheme(URI_SCHEME)
                .authority(AUTHORITY)
                .path(WORD_URI_PATH)
                .build();
    }

    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {
        // 定义Content所允许操作的三个数据列
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
    }
}