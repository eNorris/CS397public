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
    }

    public View(final String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return file.getName();
    }

    // Todo: Rename file here
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getFullPath() {
        return fullPath;
    }

    public MediaFile getFile() {
        return file;
    }

    // Todo: Update file in system
//    public void setFile(MediaFile file) {
//        this.file = file;
//    }

    public boolean isDirectory() {
        return directory;
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
