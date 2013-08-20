package dataContainers.views;

import dataContainers.IndexedFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Class that scans the filesystem and indexes all accessible files
 */
public class Crawler {
    /**
     * Begins a crawl through the designated paths and any subdirectories
     * @param paths an ArrayList containing all base directories and/or files to index
     */
    public static void instantiateCrawl(ArrayList<String> paths) {
        for (String path : paths) {
            File file = new File(path);
            crawl(file);
        }
    }

    /**
     * Indexes the passed file and recursively crawls deeper if it is a directory
     * @param file file to index
     */
    private static void crawl(File file) {
        String filename = FilenameUtils.getName(file.getName());
        if (!(filename.equals(".") || filename.equals(".."))) {
            if (file.exists()) {
                IndexedFile index = new IndexedFile(file);
                if (file.isDirectory()) {
                    crawlDirectory(file);
                }
            }
        }
    }

    /**
     * Calls the crawler to crawl all files within the directory
     * @param directory directory to be crawled
     */
    protected static void crawlDirectory(File directory) {
        for (File file : directory.listFiles()) {
            crawl(file);
        }
    }
}
