package dataContainers.views;

import dataContainers.IndexedFile;
import dataContainers.MediaFile;
import util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Playlist {
    ArrayList<IndexedFile> playlist = new ArrayList<IndexedFile>();

    public ArrayList<IndexedFile> getPlaylist() {
        return this.playlist;
    }

    public void sortByFileName() {
        Collections.sort(this.playlist, new FileNameComparator());
    }

    public void sortByFilePath() {
        Collections.sort(this.playlist, new FileNameComparator());
    }

    public void sortByFileExt() {
        Collections.sort(this.playlist, new FileNameComparator());
    }

    public void sortByFileSize() {
        Collections.sort(this.playlist, new FileNameComparator());
    }

    public void sortByFileType() {
        Collections.sort(this.playlist, new FileNameComparator());
    }
}

class FileNameComparator implements Comparator<IndexedFile> {
    public int compare(IndexedFile file1, IndexedFile file2) {
        return file1.getName().compareToIgnoreCase(file2.getName());
    }
}
class FilePathComparator implements Comparator<IndexedFile> {
    public int compare(IndexedFile file1, IndexedFile file2) {
        return file1.getFullPath().compareToIgnoreCase(file2.getFullPath());
    }
}
class FileExtComparator implements Comparator<IndexedFile> {
    public int compare(IndexedFile file1, IndexedFile file2) {
        return file1.getExt().compareToIgnoreCase(file2.getExt());
    }
}
class FileSizeComparator implements Comparator<IndexedFile> {
    public int compare(IndexedFile file1, IndexedFile file2) {
        return file1.getFile().length() <= file2.getFile().length() ? -1 : 1;
    }
}
class FileTypeComparator implements Comparator<IndexedFile> {
    public int compare(IndexedFile file1, IndexedFile file2) {
        ArrayList<MediaFile.FileTypeEnum> order = Config.fileTypeOrder();
        return order.indexOf(file1.getType()) <= order.indexOf(file2.getType()) ? -1 : 1;
    }
}
