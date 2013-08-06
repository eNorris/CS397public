package dataContainers.views;

import dataContainers.MediaFile;

import java.util.ArrayList;

public class View {
    private ArrayList<View> subViews;
    private boolean directory;    // True if view is a directory

    private String name, fullPath;
    private MediaFile file;

    public View(final MediaFile file) {
        this.file = file;
        this.name = file.getName();
    }

    public View(final String name, final String fullPath) {
        this.name = name;
        this.fullPath = fullPath;
    }

    public boolean addView(View view) {
        if (directory) {
            if (subViews.size() >= 1) {
                return false;
            }
        }
        subViews.add(view);
//        Collections.sort(subViews);     // TODO : Add filters here
        return true;
    }
}
