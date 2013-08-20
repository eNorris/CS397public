package util;

import junit.framework.Assert;
import org.junit.Test;

public class SQLBuilderTest {

    @Test
    public void addUserSucceeds() {
        final String username = "TEST";
        final String password = "PASS";
        final String emptyPassword = "";

        Assert.assertEquals(SQLBuilder.addUser(username, password),
        "Insert into User Values ('" + username + "', '" + password + "');");
        Assert.assertEquals(SQLBuilder.addUser(username, emptyPassword),
        "Insert into User Values ('" + username + "', '" + emptyPassword + "');");
    }

    @Test
    public void addUserFails() {
        final String username = "TEST";
        final String password = "PASS";
        final String empty = "";

        Assert.assertFalse(SQLBuilder.addUser(username, password).isEmpty());
        Assert.assertTrue( SQLBuilder.addUser(empty   , password).isEmpty());
        Assert.assertTrue( SQLBuilder.addUser(null    , password).isEmpty());

        Assert.assertFalse(SQLBuilder.addUser(username, empty).isEmpty());
        Assert.assertTrue( SQLBuilder.addUser(empty   , empty).isEmpty());
        Assert.assertTrue( SQLBuilder.addUser(null    , empty).isEmpty());

        Assert.assertTrue(SQLBuilder.addUser(username, null).isEmpty());
        Assert.assertTrue(SQLBuilder.addUser(empty   , null).isEmpty());
        Assert.assertTrue(SQLBuilder.addUser(null    , null).isEmpty());
    }

    @Test
    public void modifyPaths() {

    }
}
