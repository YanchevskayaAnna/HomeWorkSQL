//import service.LearningController;
//import model.Learning;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Properties;
//
///**
// * Created by sf on 03.10.16.
// */
//public class TestLearningController {
//
////    private static final String PROPERTIES_PATH = "src/main/resources/properties";
//    private static final String PROPERTIES_PATH = "D:\\ACP14\\ACP14SQL\\sql\\src\\main\\resources\\properties";
//
//    private static Connection connection;
//    private static Properties properties;
//    private static LearningController learningController;
//
//    @BeforeClass
//    public static void beforeClass() throws IOException, SQLException {
//
//        properties = new Properties();
//        properties.load(new FileInputStream(new File(PROPERTIES_PATH)));
//        connection = DriverManager.getConnection(
//                properties.getProperty("URL"),
//                properties.getProperty("USER"),
//                properties.getProperty("PASSWORD"));
//        learningController = new LearningController(connection);
//    }
//
//    @AfterClass
//    public static void afterClass() throws SQLException {
//        connection.close();
//    }
//
//    @Test
//    public void testGetAll() {
//        List<Learning> learningList = learningController.getAll();
//        Assert.assertNotNull(learningList);
//    }
//
//    @Test
//    public void getLearningByGroupId() {
//        Learning learning = learningController.getEntityById(1);
//        Assert.assertNotNull(learning);
//    }
//
//    @Test
//    public void updateTest() {
//        Learning learningBefore = learningController.getEntityById(3);
//        List<Integer> newListOfSub = Arrays.asList(5, 3);
//        List<Integer> oldSubList = learningBefore.getSubjects();
//        learningBefore.setSubjects(newListOfSub);
//        learningController.update(learningBefore);
//        Learning learningAfter = learningController.getEntityById(3);
//        Assert.assertEquals(newListOfSub, learningAfter.getSubjects());
//        learningAfter.setSubjects(oldSubList);
//        learningController.update(learningAfter);
//    }
//
//    @Test
//    public void createTest() {
//        Learning learningFromDB = learningController.getEntityById(1);
//        Learning learning = new Learning();
//        learning.setGroupID(1);
//        learning.setSubjects(Arrays.asList(1, 2, 3, 4, 5));
//        learningController.create(learning);
//        Assert.assertEquals(learningController.getEntityById(learning.getGroupID()).getSubjects(),
//                learning.getSubjects());
//
//        learningController.create(learningFromDB);
//    }
//}
