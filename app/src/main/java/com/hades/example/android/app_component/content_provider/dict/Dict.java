package com.hades.example.android.app_component.content_provider.dict;

import android.net.Uri;
import android.provider.BaseColumns;

public final class Dict {
    // 定义该ContentProvider的Authority
    public static final String AUTHORITY = "com.hades.example.android.app_component.content_provider.dict.DictContentProvider";

    // 定义一个静态内部类，定义该ContentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {
        // 定义Content所允许操作的三个数据列
        public final static String _ID = "_id";
        public final static String WORD = "word";
        public final static String DETAIL = "detail";

        // 定义该Content提供服务的两个Uri
        public final static Uri WORDS_URI = Uri.parse("content://" + AUTHORITY + "/words");
        public final static Uri WORD_URI = Uri.parse("content://" + AUTHORITY + "/word");
    }
}