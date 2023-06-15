package com.hades.example.android._feature.menu_manager.menu_page;

import androidx.annotation.NonNull;

public class VHPositionHashCodeBean {
    public int position;
    public int hashCode;


    public VHPositionHashCodeBean() {
    }

    public VHPositionHashCodeBean(int position, int hashCode) {
        this.position = position;
        this.hashCode = hashCode;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj) {
            if (obj instanceof VHPositionHashCodeBean) {
                VHPositionHashCodeBean tmp = (VHPositionHashCodeBean) obj;
                return tmp.hashCode == this.hashCode;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "pos " + position + " @ " + hashCode + "\n";
    }
}
