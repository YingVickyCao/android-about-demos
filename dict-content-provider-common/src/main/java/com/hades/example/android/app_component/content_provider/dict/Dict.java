package com.hades.example.android.app_component.content_provider.dict;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Dict {
    // 定义该ContentProvider的Authority
    public static final String AUTHORITY = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider";

    // 用于标识不同URI的常量
    private static final int URI_WORDS_LIST = 1;
    private static final int URI_WORD_ITEM = 2;

    // content://com.hades.example.android.app_component.content_provider.dict.DictContentProvider/words
    // 定义该Content提供服务的两个Uri
    // 写法1
    public final static Uri WORDS_URI = Uri.parse("content://" + AUTHORITY + "/words");

    // 写法2
    public static Uri getUri() {
        return new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path("words")
                .build();
    }

    public static Uri getUriById() {
        return new Uri.Builder()
                .scheme("content")
                .authority(AUTHORITY)
                .path("words")
                .build();
    }

    public final static Uri WORD_URI = Uri.parse("content://" + AUTHORITY + "/word");


    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {
        // 定义Content所允许操作的三个数据列
        public final static String WORD = "word";
        public final static String DETAIL = "detail";
    }
}