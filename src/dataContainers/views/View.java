package dataContainers.views;

import dataContainers.IndexedFile;
import org.apache.commons.io.FilenameUtils;
import util.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View {
    private final static View ROOT = null;
    private final static View EMPTY_VIEW = null;
    public static View ROOT_VIEW;

    static {
        ROOT_VIEW = new View(null);
    }

    private IndexedFile file;
    private View parent = null;

    private ArrayList<View> subViews;
    private ArrayList<View> subDirectories;
    private long subViewCount = 0;
    private long subDirectoryCount = 0;

    public View(final IndexedFile file) {
        this.file = file;
        if (isDirectory()) {
            subViews = new ArrayList<View>();
            subDirectories = new ArrayList<View>();
        }
    }

    /**
     * This function recursively builds a View tree.
     * @param currentFile the file for which the current View is being created.
     * @return the created view for the current file
     */
    public View rootView(IndexedFile currentFile) {
        View view = new View(currentFile);
        if (currentFile.getFile().exists()) {
            if (currentFile.isDirectory()) {
                File[] files = currentFile.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String filename = FilenameUtils.getName(file.getName());
                        if (!(filename.equals(".") || filename.equals(".."))) {
                            View child = null;
                            try {
                                child = rootView(World.getHash().get(file.getCanonicalPath()));
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            child.setParent(view);
                            view.addView(child);
//                            if (child.isDirectory()) {
//                                // TODO : Clean bubbleUp functions to one call, and make more efficient
//                                // TODO : Parent should interact with child, not the other way
//                                bubbleUpAddDirectory();
//                            } else {
//                                bubbleUpAddFile();
//                            }
                            if (child.isDirectory()) {
                                subViewCount += child.getSubViewCount();
                                subDirectoryCount += child.getSubDirectoryCount();
                            }
                            else {
                                subViewCount += 1;
                            }
                        }
                    }
                }
            }
        }
        return view;
    }

    /**
     * This function starts the initial view tree creation.
     * @param path a path to the file for the root of the View tree.
     * @return
     */
    public View rootView(String path) {
        parent = ROOT;
        // TODO : Access hashed IndexedFile here
        return rootView(new IndexedFile(path));
    }

    public String getName() {
        return file.getName();
    }

    public IndexedFile getFile() {
        return file;
    }

    public View getParent() {
        return parent;
    }

    public long getSubViews() {
        return isDirectory() ? subViews.size() : 0;
    }

    public long getSubDirectories() {
        return isDirectory() ? subDirectories.size() : 0;
    }

    public long getSubViewCount() {
        return subViewCount;
    }

    public long getSubDirectoryCount() {
        return subDirectoryCount;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public boolean addView(View view) {
        if (view.isDirectory()) {
            subDirectories.add(view);
            bubbleUpAddDirectory();
        }
        else {
            subViews.add(view);
            bubbleUpAddFile();
        }
//        Collections.sort(subViews);     // TODO : Add filters here
        return true;
    }

    /**
     * Increases the count of all parent Views by 1
     */
    public void bubbleUpAddFile() {
        this.subViewCount++;
        if (parent != ROOT) {
            parent.bubbleUpAddFile();
        }
    }

    public void bubbleUpSubFile() {
        this.subViewCount--;
        if (parent != ROOT) {
            parent.bubbleUpSubFile();
        }
    }

    public void bubbleUpAddDirectory() {
        this.subDirectoryCount++;
        if (parent != ROOT) {
            parent.bubbleUpAddDirectory();
        }
    }

    public void bubbleUpSubDirectory() {
        this.subDirectoryCount--;
        if (parent != ROOT) {
            parent.bubbleUpSubDirectory();
        }
    }

    /**
     * Recursively builds a new trimmed View tree based on a name filter.
     * @param nameFilter
     * @return new File View if the name matches, new Directory View if any subfiles match,
     * otherwise null.
     */
    private View filterByName(String nameFilter) {
        View clone = new View(this.file);
        if (isDirectory()) {
            // Currently kept separate in case a fileNameFilter is implemented
            for (View file : subViews) {
                View subView = file.filterByName(nameFilter);
                if (subView != EMPTY_VIEW) {
                    clone.addView(subView);
                    subView.setParent(clone);
                }
            }
            for (View directory : subDirectories) {
                View subDirectory = directory.filterByName(nameFilter);
                if (subDirectory != EMPTY_VIEW) {
                    clone.addView(subDirectory);
                    subDirectory.setParent(clone);
                }
            }
            if (subViews.size() + subDirectories.size() > 0) {
                return clone;
            }
        }
        else if (this.file.getName().contains(nameFilter)) {
            return clone;
        }
        return EMPTY_VIEW;
    }
}

