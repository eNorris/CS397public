package dataContainers.views;

import auxil.TestConstants;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CrawlerTest {
    private final static String ROOT_TEST_FILES = TestConstants.TEST_DIR + "/viewTest";
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

    private File root = new File(ROOT_TEST_FILES);
    private File subDir = new File(SUBDIRECTORY);
    private File emptyDir = new File(EMPTY_DIRECTORY);

    private File file1 = new File(file1Path);
    private File file2 = new File(file2Path);
    private File file3 = new File(file3Path);
    private File file4 = new File(file4Path);

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
    }

    @Test
    public void instantiateCrawlOneNode() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(file1.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(1, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(1, World.getHash().size());
        Assert.assertEquals(file1.getCanonicalPath(), World.getHash().get(file1.getCanonicalPath()).getFile().getCanonicalPath());
    }

    @Test
    public void instantiateCrawlOneDirectory() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(subDir.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(1, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(3, World.getHash().size());
        Assert.assertEquals(file3.getCanonicalPath(), World.getHash().get(file3.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file4.getCanonicalPath(),  World.getHash().get(file4.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(subDir.getCanonicalPath(), World.getHash().get(subDir.getCanonicalPath()).getFile().getCanonicalPath());
    }

    @Test
    public void instantiateCrawlTwoDirectoryPaths() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(emptyDir.getCanonicalPath());
        paths.add(subDir.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(2, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(4, World.getHash().size());
        Assert.assertEquals(file3.getCanonicalPath(), World.getHash().get(file3.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file4.getCanonicalPath(),    World.getHash().get(file4.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(subDir.getCanonicalPath(),   World.getHash().get(subDir.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(emptyDir.getCanonicalPath(), World.getHash().get(emptyDir.getCanonicalPath()).getFile().getCanonicalPath());
    }

    @Test
    public void instantiateCrawlTwoFilePaths() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(file1.getCanonicalPath());
        paths.add(file4.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(2, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(2, World.getHash().size());
        Assert.assertEquals(file1.getCanonicalPath(), World.getHash().get(file1.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file4.getCanonicalPath(),    World.getHash().get(file4.getCanonicalPath()).getFile().getCanonicalPath());
    }

    @Test
    public void instantiateCrawlFull() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(root.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(1, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(7, World.getHash().size());
        Assert.assertEquals(file1.getCanonicalPath(),    World.getHash().get(file1.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file2.getCanonicalPath(),    World.getHash().get(file2.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file3.getCanonicalPath(), World.getHash().get(file3.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(file4.getCanonicalPath(),    World.getHash().get(file4.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(root.getCanonicalPath(),     World.getHash().get(root.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(subDir.getCanonicalPath(),   World.getHash().get(subDir.getCanonicalPath()).getFile().getCanonicalPath());
        Assert.assertEquals(emptyDir.getCanonicalPath(), World.getHash().get(emptyDir.getCanonicalPath()).getFile().getCanonicalPath());
    }

    @Test
    public void instantiateCrawlFileDoesNotExist() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        File fakeFile = new File(emptyDir.getCanonicalPath() + "IdoNotExist");
        paths.add(fakeFile.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(1, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(0, World.getHash().size());
        Assert.assertNull(World.getHash().get(fakeFile.getCanonicalPath()));
    }

    @Test
    public void instantiateCrawlFilePathDuplicated() throws IOException {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(file1.getCanonicalPath());
        paths.add(file1.getCanonicalPath());
        World.getHash().clear();

        Assert.assertEquals(2, paths.size());
        Assert.assertEquals(0, World.getHash().size());
        Crawler.instantiateCrawl(paths);
        Assert.assertEquals(1, World.getHash().size());
        Assert.assertEquals(file1.getCanonicalPath(), World.getHash().get(file1.getCanonicalPath()).getFile().getCanonicalPath());
    }

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
