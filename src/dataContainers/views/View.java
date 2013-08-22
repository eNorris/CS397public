package dataContainers.views;

import dataContainers.IndexedFile;
import dataContainers.MediaFile;
import org.apache.commons.io.FilenameUtils;
import util.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View {
    private final static View EMPTY_VIEW = null;
    private final static IndexedFile ROOT_INDEX = null;
    public static View ROOT_VIEW;

    static {
        ROOT_VIEW = new View(ROOT_INDEX);
    }

    protected IndexedFile index;
    private View parent = EMPTY_VIEW;

    protected ArrayList<View> subFiles;
    protected ArrayList<View> subDirectories;
    protected long subFileCount = 0;
    protected long subDirectoryCount = 0;

    public View() {
    }

    public View(final IndexedFile file) {
        if (file == ROOT_INDEX) {
            subFiles = new ArrayList<View>();
            subDirectories = new ArrayList<View>();
            return;
        }
        if (file.isDirectory()) {
            subFiles = new ArrayList<View>();
            subDirectories = new ArrayList<View>();
        }
        this.index = file;
    }

    /**
     * This function recursively builds a View tree.
     * @param currentFile the index for which the current View is being created.
     * @return the created view for the current index.
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
                            View child = EMPTY_VIEW;
                            try {
                                child = rootView(World.getHash().get(file.getCanonicalPath()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            child.setParent(view);
                            view.addView(child);
                        }
                    }
                }
            }
        }
        return view;
    }

    /**
     * This function starts the initial view tree creation.
     * @param path a path to the index for the root of the View tree.
     * @return the view created for the passed path.
     */
    public View rootView(String path) {
        IndexedFile file = ROOT_INDEX;
        try {
            file = World.getHash().get(new File(path).getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file == ROOT_INDEX) {
            file = new IndexedFile(path);
            World.getHash().put(file.getFullPath(), file);
        }
        View view = rootView(file);
        view.setParent(this);
        if (view.getIndex().isDirectory()) {
            subDirectories.add(view);
        }
        else {
            subFiles.add(view);
        }
        return view;
    }

    public String getName() {
        return index.getName();
    }

    public IndexedFile getIndex() {
        return index;
    }

    public View getParent() {
        return parent;
    }

    public long getSubFiles() {
        return index.isDirectory() ? subFiles.size() : 0;
    }

    public long getSubDirectories() {
        return index.isDirectory() ? subDirectories.size() : 0;
    }

    public long getSubFileCount() {
        return subFileCount;
    }

    public long getSubDirectoryCount() {
        return subDirectoryCount;
    }

    public void setParent(View parent) {
        this.parent = parent;
    }

    public boolean addView(View view) {
        if (view.getIndex().isDirectory()) {
            this.subDirectories.add(view);
            this.subDirectoryCount += 1 + view.getSubDirectoryCount();
            this.subFileCount += view.getSubFileCount();
        }
        else {
            this.subFiles.add(view);
            this.subFileCount += 1;
        }
//        Collections.sort(subFiles);     // TODO : Add filters here
        return true;
    }

    /**
     * Increases the count of all parent Views by 1
     */
    public void bubbleUpAddFile() {
        this.subFileCount++;
        if (parent != ROOT_VIEW) {
            parent.bubbleUpAddFile();
        }
    }

    public void bubbleUpSubFile() {
        this.subFileCount--;
        if (parent != ROOT_VIEW) {
            parent.bubbleUpSubFile();
        }
    }

    public void bubbleUpAddDirectory() {
        this.subDirectoryCount++;
        if (parent != ROOT_VIEW) {
            parent.bubbleUpAddDirectory();
        }
    }

    public void bubbleUpSubDirectory() {
        this.subDirectoryCount--;
        if (parent != ROOT_VIEW) {
            parent.bubbleUpSubDirectory();
        }
    }

    private void addSubFileCount(long count) {
        this.subFileCount += count;
    }

    private void addSubDirectoryCount(long count) {
        this.subDirectoryCount += count;
    }

    // TODO : Find a way to reuse most of the filter code, duplication is bad!!!
    /**
     * Recursively builds a new trimmed View tree based on a name filter.
     * @param nameFilter String all accepted file-names(no path or extension) must contain.
     * @return new File View if the name matches, new Directory View if any sub-files match,
     * otherwise null.
     */
    private View filterByName(String nameFilter) {
        View clone = new View(this.index);
        if (index.isDirectory()) {
            // Currently kept separate in case a fileNameFilter is implemented
            for (View file : subFiles) {
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
            if (subFiles.size() + subDirectories.size() > 0) {
                return clone;
            }
        }
        else if (this.index.getName().contains(nameFilter)) {
            return clone;
        }
        return EMPTY_VIEW;
    }

    /**
     * Recursively builds a new trimmed View tree based on an extension filter.
     * @param extFilter ArrayList of all accepted extensions.
     * @return new File View if an ext matches, new Directory View if any sub-files match,
     * otherwise null.
     */
    private View filterByExtension(ArrayList<String> extFilter) {
        View clone = new View(this.index);
        if (index.isDirectory()) {
            // Currently kept separate in case a fileNameFilter is implemented
            for (View file : subFiles) {
                View subView = file.filterByExtension(extFilter);
                if (subView != EMPTY_VIEW) {
                    clone.addView(subView);
                    subView.setParent(clone);
                }
            }
            for (View directory : subDirectories) {
                View subDirectory = directory.filterByExtension(extFilter);
                if (subDirectory != EMPTY_VIEW) {
                    clone.addView(subDirectory);
                    subDirectory.setParent(clone);
                }
            }
            if (subFiles.size() + subDirectories.size() > 0) {
                return clone;
            }
        }
        else if (matchesExtension(this.index.getExt(), extFilter)) {
            return clone;
        }
        return EMPTY_VIEW;
    }

    /**
     * Determines if extensions contains toMatch.
     * @param toMatch string to look for in the list of extensions.
     * @param extensions list of extensions being filtered for.
     * @return true if toMatch matches an extension in extensions.
     */
    public boolean matchesExtension(String toMatch, ArrayList<String> extensions) {
        for (String extension : extensions) {
            if (toMatch.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recursively builds a new trimmed View tree based on a type filter.
     * @param types ArrayList of all accepted types.
     * @return new File View if a type matches, new Directory View if any sub-files match,
     * otherwise null.
     */
    private View filterByType(ArrayList<MediaFile.FileTypeEnum> types) {
        View clone = new View(this.index);
        if (index.isDirectory()) {
            // Currently kept separate in case a fileNameFilter is implemented
            for (View file : subFiles) {
                View subView = file.filterByType(types);
                if (subView != EMPTY_VIEW) {
                    clone.addView(subView);
                    subView.setParent(clone);
                }
            }
            for (View directory : subDirectories) {
                View subDirectory = directory.filterByType(types);
                if (subDirectory != EMPTY_VIEW) {
                    clone.addView(subDirectory);
                    subDirectory.setParent(clone);
                }
            }
            if (subFiles.size() + subDirectories.size() > 0) {
                return clone;
            }
        }
        else if (matchesType(this.index.getType(), types)) {
            return clone;
        }
        return EMPTY_VIEW;
    }

    /**
     * Determines if types contains toMatch.
     * @param toMatch string to look for in the list of types.
     * @param types list of types being filtered for.
     * @return true if toMatch matches an extension in types.
     */
    public boolean matchesType(MediaFile.FileTypeEnum toMatch, ArrayList<MediaFile.FileTypeEnum> types) {
        for (MediaFile.FileTypeEnum type : types) {
            if (type.equals(MediaFile.FileTypeEnum.O)) {
                return true;
            }
            if (type.equals(MediaFile.FileTypeEnum.A)) {
                if (toMatch.equals(MediaFile.FileTypeEnum.A)
                        || toMatch.equals(MediaFile.FileTypeEnum.S)) {
                    return true;
                }
            }
            if (type.equals(MediaFile.FileTypeEnum.V)) {
                if (toMatch.equals(MediaFile.FileTypeEnum.V)
                        || toMatch.equals(MediaFile.FileTypeEnum.M)
                        || toMatch.equals(MediaFile.FileTypeEnum.T)) {
                    return true;
                }
            }
            if (toMatch.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether this view is the root view.
     * @return true if this is the root view, false otherwise.
     */
    public boolean isRoot() {
        return this.equals(ROOT_VIEW);
    }
}

