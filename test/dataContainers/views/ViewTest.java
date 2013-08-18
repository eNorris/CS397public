package dataContainers.views;

import dataContainers.IndexedFile;
import junit.framework.Assert;
import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ViewTest {

    private final static String ROOT_TEST_FILES = "testFiles/viewTest";
    private final static String SUBDIRECTORY = ROOT_TEST_FILES + "/" + "subDir";
    private final static String EMPTY_DIRECTORY = ROOT_TEST_FILES + "/" + "emptyDir";
    private final String file1Name = "TestA";
    private final String file2Name = "TestFileA";
    private final String file3Name = "TestDepthB";
    private final String file4Name = "TestDepthC";
    private final String file1Path = ROOT_TEST_FILES + "/" + file1Name;
    private final String file2Path = ROOT_TEST_FILES + "/" + file2Name;
    private final String file3Path = SUBDIRECTORY + "/" + file3Name;
    private final String file4Path = SUBDIRECTORY + "/" + file4Name;
    private ArrayList<File> files = new ArrayList<File>();

    private File root = new File(ROOT_TEST_FILES);
    private File subDir = new File(SUBDIRECTORY);
    private File emptyDir = new File(EMPTY_DIRECTORY);

    private File file1 = new File(file1Path);
    private File file2 = new File(file2Path);
    private File file3 = new File(file3Path);
    private File file4 = new File(file4Path);

    private IndexedFile indexRoot;
    private IndexedFile indexSubDir;
    private IndexedFile indexEmptyDir;

    private IndexedFile index1;
    private IndexedFile index2;
    private IndexedFile index3;
    private IndexedFile index4;

    @Before
    public void setUp() {
        root.mkdirs();
        subDir.mkdirs();
        emptyDir.mkdirs();
        try {
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
            file4.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        indexRoot = new IndexedFile(root);
        indexSubDir = new IndexedFile(subDir);
        indexEmptyDir = new IndexedFile(emptyDir);

        index1 = new IndexedFile(file1);
        index2 = new IndexedFile(file2);
        index3 = new IndexedFile(file3);
        index4 = new IndexedFile(file4);
    }

    @Test
    public void createFileView() throws IOException {
        View view = new View(index1);

        Assert.assertEquals(file1, view.getIndex().getFile());
        Assert.assertEquals(file1.getCanonicalPath(), view.getIndex().getFullPath());
        Assert.assertEquals(FilenameUtils.getBaseName(file1.getName()), view.getName());
        Assert.assertEquals(file1.isDirectory(), view.getIndex().isDirectory());
        Assert.assertEquals(false, view.getIndex().isDirectory());
        Assert.assertEquals(null, view.getParent());
    }

    @Test
    public void createDirectoryView() throws IOException {
        View view = new View(indexRoot);

        Assert.assertEquals(root, view.getIndex().getFile());
        Assert.assertEquals(root.getCanonicalPath(), view.getIndex().getFullPath());
        Assert.assertEquals(FilenameUtils.getBaseName(root.getName()), view.getName());
        Assert.assertEquals(root.isDirectory(), view.getIndex().isDirectory());
        Assert.assertEquals(true, view.getIndex().isDirectory());
        Assert.assertEquals(null, view.getParent());
    }

    @Test
    public void createViewTreeEmptyDir() throws IOException {
        View root = new View(null);
        View view = root.rootView(EMPTY_DIRECTORY);

        Assert.assertEquals(0, view.getSubViews());
        Assert.assertEquals(0, view.getSubDirectories());
        Assert.assertEquals(0, view.getSubViewCount());
        Assert.assertEquals(0, view.getSubDirectoryCount());
        Assert.assertEquals(true, view.getIndex().isDirectory());
    }

    @Test
    public void createViewTreeFile() {
        View root = new View(null);
        View view = root.rootView(file1Path);

        Assert.assertEquals(0, view.getSubViews());
        Assert.assertEquals(0, view.getSubDirectories());
        Assert.assertEquals(0, view.getSubViewCount());
        Assert.assertEquals(0, view.getSubDirectoryCount());
        Assert.assertEquals(false, view.getIndex().isDirectory());
    }

//    @Test
//    public void createViewTreeFull() {
//        View root = new View(null);
//        View view = root.rootView(ROOT_TEST_FILES);
//
//        Assert.assertEquals(2, view.getSubViews());
//        Assert.assertEquals(2, view.getSubDirectories());
//        Assert.assertEquals(2, view.getSubViewCount());
//        Assert.assertEquals(0, view.getSubDirectoryCount());
//        Assert.assertEquals(true, view.getIndex().isDirectory());
//    }

    @After
    public void cleanUp() {
        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
        subDir.delete();
        emptyDir.delete();
        root.delete();
    }
}
