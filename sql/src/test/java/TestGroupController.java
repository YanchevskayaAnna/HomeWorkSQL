import dao.controller.GroupController;
import dao.model.Group;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by sf on 03.10.16.
 */
public class TestGroupController {

    private static final String PROPERTIES_PATH = "src/main/resources/properties";

    private static Connection connection;
    private static Properties properties;
    private static GroupController groupController;

    @BeforeClass
    public static void beforeClass() throws IOException, SQLException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
        connection = DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASSWORD"));
        groupController = new GroupController(connection);
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        connection.close();
    }

    @Test
    public void getAllGroups() {
        List<Group> groupList = groupController.getAll();
        Assert.assertNotNull(groupList);
    }

    @Test
    public void getGroupByID() {
        Group group = groupController.getEntityById(1);
        Assert.assertNotNull(group);
    }

    @Test
    public void updateGroupsInfo() {

        Group groupBeforeUpdate = groupController.getEntityById(1);

        String newName = "Middles";
        String oldName = groupBeforeUpdate.getName();
        groupBeforeUpdate.setName(newName);

        groupController.update(groupBeforeUpdate);
        Group groupAfterUpd = groupController.getEntityById(1);
        Assert.assertEquals(groupBeforeUpdate.getId(), groupAfterUpd.getId());
        Assert.assertEquals(groupBeforeUpdate.getName(), groupAfterUpd.getName());

        groupBeforeUpdate.setName(oldName);

        groupController.update(groupBeforeUpdate);
    }

    @Test
    public void deleteGroup() {
        List<Group> groupList = groupController.getAll();
        Group testGroup = groupList.get(groupList.size() - 1);
        groupController.delete(connection, "groups", testGroup.getId());
        Assert.assertNull(groupController.getEntityById(testGroup.getId()));

        groupController.create(testGroup);
    }

    @Test
    public void createGroup() {
        Group group = new Group();
        group.setName("Test2390");
        Assert.assertTrue(groupController.create(group));
        List<Group> groupList = groupController.getAll();
        groupController.delete(connection, "groups", groupList.get(groupList.size() - 1).getId());
    }
}
