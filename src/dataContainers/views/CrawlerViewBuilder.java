package dataContainers.views;

import dataContainers.IndexedFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class CrawlerViewBuilder extends Crawler {
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
}
