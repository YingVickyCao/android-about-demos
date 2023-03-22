package com.hades.example.android._case.parcle;

import android.os.Parcel;
import android.os.Parcelable;

public class InitBean implements Parcelable {
    private boolean flag;
    private String uniqueKey;

    public InitBean(boolean flag, String uniqueKey) {
        this.flag = flag;
        this.uniqueKey = uniqueKey;
    }


    public String getUniqueKey() {
        return uniqueKey;
    }

    public boolean isFlag() {
        return flag;
    }

    // Parcelable, START
    protected InitBean(Parcel in) {
        flag = in.readByte() != 0;
        // ERROR: Caused by java.lang.RuntimeException, Parcel android.os.Parcel@1bf4a17f: Unmarshalling unknown type code 717174 at offset 500
//        if (in.readByte() != 0) {
//            uniqueKey = in.readString();
//        }
        // Reason: Read （InitBean(Parcel in)） 和 write （writeToParcel）的顺序should 一致
        // Fix:
        if (flag){
            uniqueKey = in.readString();
        }
    }

    public static final Creator<InitBean> CREATOR = new Creator<InitBean>() {
        @Override
        public InitBean createFromParcel(Parcel in) {
            return new InitBean(in);
        }

        @Override
        public InitBean[] newArray(int size) {
            return new InitBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (flag ? 1 : 0));
        dest.writeString(uniqueKey);
    }
    // Parcelable,END
}
