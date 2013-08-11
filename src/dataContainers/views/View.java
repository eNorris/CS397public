package dataContainers.views;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View {
    private final static View ROOT = null;
    private final static View EMPTY_VIEW = null;

    private View parent;
    private boolean directory;    // True if view is a directory
    private String fullPath, name;
    private File file;

    private ArrayList<View> subViews;
    private ArrayList<View> subDirectories;
    private long subViewCount = 0;
    private long subDirectoryCount = 0;

    public View(final File file) throws IOException {
        this.file = file;
        this.fullPath = file.getCanonicalPath();
        this.name = FilenameUtils.getBaseName(this.fullPath);
        this.directory = file.isDirectory();
//        this.ext = FilenameUtils.getExtension(this.fullPath);
    }

    /**
     * This function recursively builds a View tree.
     * @param currentFile the file for which the current View is being created.
     * @return the created view for the current file
     * @throws IOException
     */
    public View rootView(File currentFile) throws IOException {
        View view = new View(currentFile);
        if (currentFile.exists()) {
            if (isDirectory()) {
                File[] files = currentFile.listFiles();
                assert files != null;
                for (File file : files) {
                    String filename = FilenameUtils.getName(file.getName());
                    if (filename.equals(".") || filename.equals("..")) {
                        View child = rootView(file);
                        child.setParent(this);
                        addView(child);
                    }
                }
            }
        }
        return view;
    }

    /**
     * This function starts the initial view tree creation.
     * @param path a path to the file for the root of the View tree.
     * @throws IOException
     */
    public void rootView(String path) throws IOException {
        parent = ROOT;
        rootView(new File(path));
    }

    public String getName() {
        return file.getName();
    }

    public String getFullPath() {
        return fullPath;
    }

    public File getFile() {
        return file;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    // Todo: Update file in system
//    public void setFile(MediaFile file) {
//        this.file = file;
//    }

    public boolean isDirectory() {
        return directory;
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
     * @throws IOException
     */
    private View filterByName(String nameFilter) throws IOException {
        View clone = new View(this.file);
        if (isDirectory()) {
            // Currently kept seperate in case a fileNameFilter is implemented
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
        else if (this.name.contains(nameFilter)) {
            return clone;
        }
        return EMPTY_VIEW;
    }
}

