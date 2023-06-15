package com.hades.example.android._feature.menu_manager.menu_page;

public class OpenedPage {
    private String title;
    private String childTitle;
    private String type;
    private boolean isGroup;

    public OpenedPage() {
    }

    public OpenedPage(String title, String childTitle, String type, boolean isGroup) {
        this.title = title;
        this.childTitle = childTitle;
        this.type = type;
        this.isGroup = isGroup;
    }

    public void updateOpenedPage(String title, String childTitle, String type, boolean isGroup) {
        this.title = title;
        this.childTitle = childTitle;
        this.type = type;
        this.isGroup = isGroup;
    }

    public String getTitle() {
        return title;
    }

    public String getChildTitle() {
        return childTitle;
    }

    public String getType() {
        return type;
    }

    public boolean isGroup() {
        return isGroup;
    }
}