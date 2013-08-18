package dataContainers;

import org.apache.commons.io.FilenameUtils;
import util.World;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IndexedFile extends Component {

    protected File file;
    protected String fullPath;      // The full file path ("C:\temp\file.txt" => "C:\temp\file.txt")
    protected String name;          // Name of the file without extension ("C:\temp\file.txt" => "file")
    protected String ext = "";      // Extension of the file ("C:\temp\file.txt" => "txt")*/
    protected boolean directory;    // True if view is a directory

    protected MediaLibrary owner = null; // TODO : Remove after decoupling wall and file structures
    /**
     * Base constructor
     * @param file - The file that this mediafile links to
     * @param owner - medialibrary that owns the file
     */
    public IndexedFile(File file, MediaLibrary owner){
        setFile(file);
        this.owner = owner;
    }

    /**
     * String constructor
     * @param filePath - fully qualified path to the file
     * @param owner - medialibrary that owns the file
     */
    public IndexedFile(String filePath, MediaLibrary owner){
        this(new File(filePath), owner);
    }

    public IndexedFile(String path) {
        this(new File(path));
    }

    public IndexedFile(File file) {
        if (file != null) {
            this.file = file;
            try {
                this.fullPath = file.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.name = FilenameUtils.getBaseName(this.fullPath);
            this.ext = FilenameUtils.getExtension(this.fullPath);
            this.directory = file.isDirectory();
            World.getHash().put(fullPath, this);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public boolean isDirectory() {
        return directory;
    }

    public boolean exists() {
        return file.exists();
    }

    // TODO : Replace with IndexedFile[] after the full hashtable is implemented
    public File[] listFiles() {
        return file.listFiles();
    }

    public void rename(String name) {
        // TODO : Decide if should be just file name or full path
    }
}
