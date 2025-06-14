package com.example.kotlin.test.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// onDelete = menu 删除时，PageData 会怎样变化
// onUpdate = menu 更新时，PageData 会怎样变化
@Entity(tableName = "page_data", foreignKeys = {@ForeignKey(entity = Menu.class,
        parentColumns = "code", /* parentColumns - Menu.class - code */
        childColumns = "code", /* childColumns - PageData.class - code */
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)})
public class PageData {
    @PrimaryKey
    public int id;

    public int code;

    public String title;

    @Override
    public String toString() {
        return "PageData{" +
                "id=" + id +
                ", code=" + code +
                ", title='" + title + '\'' +
                '}';
    }
}
