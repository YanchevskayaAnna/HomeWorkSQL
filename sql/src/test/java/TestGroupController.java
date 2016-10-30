import dao.GroupDAOImpl;
import dao.LearningDAOImpl;
import model.Student;
import org.junit.*;
import service.AdminController;
import model.Group;
import model.Subject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sf on 03.10.16.
 */
public class TestGroupController {

    private static final String PROPERTIES_PATH = "sql/src/main/resources/properties";

    private static Connection connection;
    private static Properties properties;
    private static AdminController groupController;

    @BeforeClass
    public static void beforeClass() throws IOException, SQLException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
        connection = DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASSWORD"));
        groupController = new AdminController(new GroupDAOImpl(connection), new LearningDAOImpl(connection), null, null, null);
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        connection.close();
    }

    @Test
    public void getAllGroups() {
        List<Group> groupList = groupController.getAllGroups();
        Assert.assertNotNull(groupList);
    }

    @Test
    public void getGroupByID() {
        Group group = groupController.getGroupById(1);
        Assert.assertNotNull(group);
    }

    @Test
    public void updateGroupsInfo() {

        Group groupBeforeUpdate = groupController.getGroupById(1);

        String newName = "Middles";
        String oldName = groupBeforeUpdate.getName();
        groupBeforeUpdate.setName(newName);

        groupController.updateGroup(groupBeforeUpdate);
        Group groupAfterUpd = groupController.getGroupById(1);
        Assert.assertEquals(groupBeforeUpdate.getId(), groupAfterUpd.getId());
        Assert.assertEquals(groupBeforeUpdate.getName(), groupAfterUpd.getName());

        groupBeforeUpdate.setName(oldName);

        groupController.updateGroup(groupBeforeUpdate);
    }

    @Test
    public void deleteGroup() {
        List<Group> groupList = groupController.getAllGroups();
        Group testGroup = groupList.get(groupList.size() - 1);
//        groupController.deleteGroup(connection, "groups", testGroup.getId());
//        Assert.assertNull(groupController.getGroupById(testGroup.getId()));

        groupController.createGroup(testGroup);
    }

    @Test
    public void createGroup() {
        Group group = new Group();
        group.setName("Test2390");

        Assert.assertTrue(groupController.createGroup(group));
        List<Group> groupList = groupController.getAllGroups();
//        groupController.deleteGroup(connection, "groups", groupList.get(groupList.size() - 1).getId());
    }

    @Test
    public void getAllGroupsFromResultSet() throws SQLException {
        Subject subject = new Subject();
        subject.setName("Math");
        List<Group> groupList = groupController.getAllGroupsWithSubject(subject);
        Assert.assertNotNull(groupList);
    }

    @Test
    public void getAverageMarks() throws SQLException {
        Map<Student, Integer> groupList = groupController.getAverageRating();
        Assert.assertNotNull(groupList);
    }

    @Test
       public void getAverageRatingSubject() throws SQLException {
        Assert.assertEquals(7, groupController.getAverageRating(1));
        Assert.assertEquals(8, groupController.getAverageRating(2));
    }

    @Test
    public void getAverageRatingGroup() throws SQLException {
        Assert.assertEquals(7, groupController.getAverageRatingGroup(1));
        Assert.assertEquals(9, groupController.getAverageRatingGroup(2));
    }

    @Test
    public void getAverageRatingSubjectGroup() throws SQLException {
        Assert.assertEquals(5, groupController.getAverageRating(1, 1));
        Assert.assertEquals(9, groupController.getAverageRating(2, 1));
    }


}
