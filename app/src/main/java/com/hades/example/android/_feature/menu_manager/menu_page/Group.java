package com.hades.example.android._feature.menu_manager.menu_page;

import java.util.List;

public class Group {
    private String title;
    private int id;
    private List<Child> children;

    public Group(String title, int id, List<Child> items) {
        this.title = title;
        this.id = id;
        this.children = items;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<Child> getChildren() {
        return children;
    }

}