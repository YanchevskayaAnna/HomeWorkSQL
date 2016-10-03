import dao.controller.TeacherController;
import dao.model.Teacher;
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
public class TestTeacherController {

    private static final String PROPERTIES_PATH = "src/main/resources/properties";

    private static Connection connection;
    private static Properties properties;
    private static TeacherController teacherController;

    @BeforeClass
    public static void beforeClass() throws IOException, SQLException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
        connection = DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASSWORD"));
        teacherController = new TeacherController(connection);
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        connection.close();
    }

    @Test
    public void getAllTeachers() {
        List<Teacher> teachers = teacherController.getAll();
        Assert.assertNotNull(teachers);
    }

    @Test
    public void getTeacherByID(){
        Teacher teacher = teacherController.getEntityById(1);
        Assert.assertNotNull(teacher);
    }

    @Test
    public void updateTest() {
        Teacher teacherBeforeUpd = teacherController.getEntityById(1);
        Teacher teacherForUpd = teacherController.getEntityById(1);


        String newFirstName = "Bob";
        String newLastName = "Jons";

        teacherForUpd.setFirstName(newFirstName);
        teacherForUpd.setLastName(newLastName);

        teacherController.update(teacherForUpd);
        Teacher teacherAfterUpd = teacherController.getEntityById(1);
        Assert.assertEquals(teacherForUpd.getId(), teacherAfterUpd.getId());
        Assert.assertEquals(teacherForUpd.getFirstName(), teacherAfterUpd.getFirstName());
        Assert.assertEquals(teacherForUpd.getLastName(), teacherAfterUpd.getLastName());
        Assert.assertEquals(teacherForUpd.getId(), teacherAfterUpd.getId());
        Assert.assertEquals(teacherForUpd.getExp(), teacherAfterUpd.getExp(), 0.02);

        teacherController.update(teacherBeforeUpd);
    }

    @Test
    public void deleteTeacher() {
        List<Teacher> teachers = teacherController.getAll();
        Teacher testTeacher = teachers.get(teachers.size() - 1);
        teacherController.delete(connection, "teachers", testTeacher.getId());
        Assert.assertNull(teacherController.getEntityById(testTeacher.getId()));

        teacherController.create(testTeacher);
    }

    @Test
    public void createTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Test");
        teacher.setLastName("Test");
        teacher.setExp(444);
        teacher.setSubjectId(1);
        Assert.assertTrue(teacherController.create(teacher));
        List<Teacher> teachers = teacherController.getAll();
        teacherController.delete(connection, "teachers", teachers.get(teachers.size() - 1).getId());
    }

}
