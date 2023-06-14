package com.hades.example.android._feature.menu_manager;

import java.util.List;

public class Menu {
    int id;
    String title;
    String code;
    String type; // activity,fragment
    String desc;
    List<Menu> items;

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", items=" + items +
                '}';
    }
}
