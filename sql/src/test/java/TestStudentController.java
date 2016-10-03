import dao.controller.StudentController;
import dao.model.Student;
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
public class TestStudentController {

    private static final String PROPERTIES_PATH = "src/main/resources/properties";

    private static Connection connection;
    private static Properties properties;
    private static StudentController studentController;

    @BeforeClass
    public static void beforeClass() throws IOException, SQLException {

        properties = new Properties();
        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
        connection = DriverManager.getConnection(
                properties.getProperty("URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASSWORD"));
        studentController = new StudentController(connection);
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        connection.close();
    }

    @Test
    public void getAllStudents() {
        List<Student> students = studentController.getAll();
        Assert.assertNotNull(students);
    }

    @Test
    public void getStudentByID() {
        Student student = studentController.getEntityById(1);
        Assert.assertNotNull(student);
    }

    @Test
    public void updateStudentsInfo() {

        Student studentBeforeUpd = studentController.getEntityById(1);
        Student studentForUpd = studentController.getEntityById(1);

        String newFirstName = "Bob";
        String newLastName = "Jons";

        studentForUpd.setFirstName(newFirstName);
        studentForUpd.setLastName(newLastName);

        studentController.update(studentForUpd);
        Student studentAfterUpd = studentController.getEntityById(1);
        Assert.assertEquals(studentForUpd.getId(), studentAfterUpd.getId());
        Assert.assertEquals(studentForUpd.getFirstName(), studentAfterUpd.getFirstName());
        Assert.assertEquals(studentForUpd.getLastName(), studentAfterUpd.getLastName());
        Assert.assertEquals(studentForUpd.getGroupID(), studentAfterUpd.getGroupID());

        studentController.update(studentBeforeUpd);

    }

    @Test
    public void deleteStudent() {
        List<Student> students = studentController.getAll();
        Student testStudent = students.get(students.size() - 1);
        studentController.delete(connection, "students", testStudent.getId());
        Assert.assertNull(studentController.getEntityById(testStudent.getId()));

        studentController.create(testStudent);
    }

    @Test
    public void createStudent() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Test");
        student.setGroupID(2);
        Assert.assertTrue(studentController.create(student));
        List<Student> students = studentController.getAll();
        studentController.delete(connection, "students", students.get(students.size() - 1).getId());
    }
}
