package dataContainers;

import auxil.TestConstants;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.World;

import java.io.File;
import java.io.IOException;

public class IndexedFileTest {

    private String FILE_PATH = "testFile";
    private File directory = new File(TestConstants.TEST_DIR);
    private File file = new File(TestConstants.TEST_DIR + "/" + FILE_PATH);

    @Before
    public void setUp() throws IOException {
        directory.mkdirs();
        file.createNewFile();
    }

    @Test
    public void newIndexedFile() throws IOException {
        IndexedFile index = new IndexedFile(file);

        Assert.assertEquals(file, index.getFile());
        Assert.assertEquals(file.getCanonicalPath(), index.getFile().getCanonicalPath());
        Assert.assertFalse(index.isDirectory());
        Assert.assertFalse(index.getFile().isDirectory());
        Assert.assertEquals(index, World.getHash().get(file.getCanonicalPath()));
    }

    @Test
    public void newIndexedDirectory() throws IOException {
        IndexedFile index = new IndexedFile(directory);

        Assert.assertEquals(directory, index.getFile());
        Assert.assertEquals(directory.getCanonicalPath(), index.getFile().getCanonicalPath());
        Assert.assertTrue(index.isDirectory());
        Assert.assertTrue(index.getFile().isDirectory());
        Assert.assertEquals(index, World.getHash().get(directory.getCanonicalPath()));
    }

    @Test
    public void getFile() {
        IndexedFile index = new IndexedFile(file);
        Assert.assertEquals(file, index.getFile());
    }

    @After
    public void tearDown() {
        file.delete();
        directory.delete();
    }
}