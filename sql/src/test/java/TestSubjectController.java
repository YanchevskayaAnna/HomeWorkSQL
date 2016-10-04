import dao.controller.StudentController;
import dao.controller.SubjectController;
import dao.model.Subject;
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
public class TestSubjectController {

    private static final String PROPERTIES_PATH = "src/main/resources/properties";

    private static Connection connection;
    private static Properties properties;
    private static SubjectController subjectController;

    @BeforeClass
    public static void beforeClass() throws IOException, SQLException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
        connection = DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASSWORD"));
        subjectController = new SubjectController(connection);
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        connection.close();
    }

    @Test
    public void getAllSubjects() {
        List<Subject> subjects = subjectController.getAll();
        Assert.assertNotNull(subjects);
    }

    @Test
    public void getSubjectByID() {
        Subject subject = subjectController.getEntityById(1);
        Assert.assertNotNull(subject);
    }

    @Test
    public void updateTest() {

        Subject subjectBeforeUpd = subjectController.getEntityById(1);
        Subject subjectForUpd = subjectController.getEntityById(1);

        String newTitle = "Drink";
        String newDescp = "Beer and other";

        subjectForUpd.setName(newTitle);
        subjectForUpd.setDescription(newDescp);

        subjectController.update(subjectForUpd);
        Subject subjectAfterUpd = subjectController.getEntityById(1);
        Assert.assertEquals(subjectForUpd.getId(), subjectAfterUpd.getId());
        Assert.assertEquals(subjectForUpd.getName(), subjectAfterUpd.getName());
        Assert.assertEquals(subjectForUpd.getDescription(), subjectAfterUpd.getDescription());

        subjectController.update(subjectBeforeUpd);
    }

    @Test
    public void deleteSubject() {
        List<Subject> subjects = subjectController.getAll();
        Subject testSubject = subjects.get(subjects.size() - 1);
        subjectController.delete(connection, "subjects", testSubject.getId());
        Assert.assertNull(subjectController.getEntityById(testSubject.getId()));

        subjectController.create(testSubject);
    }

    @Test
    public void createSubject() {
        Subject subject = new Subject();
        subject.setName("Test");
        subject.setDescription("Test");
        Assert.assertTrue(subjectController.create(subject));
        List<Subject> subjects = subjectController.getAll();
        subjectController.delete(connection, "subjects", subjects.get(subjects.size() - 1).getId());
    }

    @Test
    public void getHumSubs(){
        List<Subject> subjectList = subjectController.getHumSubjects();
        boolean res = true;
        for (Subject subject : subjectList) {
            if(!subject.getDescription().equals("Hum")) {
                res = false;
                break;
            }
        }
        Assert.assertTrue(res);
    }
}
