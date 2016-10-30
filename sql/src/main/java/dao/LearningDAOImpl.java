package dao;

import model.Group;
import model.Learning;
import model.Student;
import model.Subject;

import java.sql.*;
import java.util.*;

public class LearningDAOImpl implements LearningDAO {

    private Connection connection;

    public LearningDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Learning> getAll() {
        List<Learning> learningList = new ArrayList<>();
        Set<Integer> groupsSet = new HashSet<>();
        String query = "SELECT groups.group_id gid, subjects.subject_id sid FROM groups LEFT JOIN learning ON" +
                " groups.group_id=learning.group_id LEFT JOIN subjects ON learning.subject_id=subjects.subject_id;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Integer groupID = resultSet.getInt("gid");
                Integer subjectID = resultSet.getInt("sid");
                if (groupID != null && subjectID != null) {
                    if (!groupsSet.contains(groupID)) {
                        Learning learning = new Learning();
                        learning.setGroupID(groupID);
                        learning.addSub(subjectID);
                        learningList.add(learning);
                        groupsSet.add(learning.getGroupID());
                    } else {
                        Learning learning = findLearningById(learningList, groupID);
                        learning.addSub(subjectID);
                    }
                }
            }
            return learningList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Learning getEntityById(Integer id) {

        Learning learning = new Learning();
        String query = "SELECT groups.group_id gid, subjects.subject_id sid FROM groups LEFT JOIN learning ON" +
                " groups.group_id=learning.group_id LEFT JOIN subjects ON learning.subject_id=subjects.subject_id " +
                "WHERE groups.group_id=" + id + ";";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                learning.setGroupID(resultSet.getInt("gid"));
                learning.addSub(resultSet.getInt("sid"));
            }

            while (resultSet.next()) {
                learning.addSub(resultSet.getInt("sid"));
            }
            return learning;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Learning entity) {
        String sqlQueryForDel = "DELETE FROM learning WHERE group_id=?;";
        String sqlQueryForAdd = "INSERT INTO learning (group_id, subject_id) VALUES (?,?)";

        try (PreparedStatement preparedStatementDel = connection.prepareStatement(sqlQueryForDel);
             PreparedStatement preparedStatementAdd = connection.prepareStatement(sqlQueryForAdd);) {
            preparedStatementDel.setInt(1, entity.getGroupID());
            preparedStatementDel.execute();

            for (Integer integer : entity.getSubjects()) {
                preparedStatementAdd.setInt(1, entity.getGroupID());
                preparedStatementAdd.setInt(2, integer);
                preparedStatementAdd.execute();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(Learning entity) {
        return update(entity);
    }

    private static Learning findLearningById(List<Learning> learnings, int id) {
        for (Learning learning : learnings) {
            if (learning.getGroupID() == id) {
                return learning;
            }
        }
        return null;
    }

    @Override
    public int getAverageRatingGroup(int groupID) {
        String query =
                "SELECT AVG(marks.mark) as mark  " +
                " FROM marks" +
                " LEFT JOIN students ON marks.student_id = students.student_id" +
                " WHERE students.group_id = ?;";

        try (PreparedStatement preparedStatementSelect = connection.prepareStatement(query);) {
            preparedStatementSelect.setInt(1, groupID);
            ResultSet resultSet = preparedStatementSelect.executeQuery();

            while (resultSet.next()) {
                int mark = resultSet.getInt("mark");
                System.out.println("average mark group: " + groupID + "mark:" + mark);
                return mark;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }

    @Override
    public int getAverageRating(int subjectID)  {

        String query = "SELECT AVG(marks.mark) as mark  " +
                " FROM marks" +
                " WHERE marks.subject_id = ?;";

        try (PreparedStatement preparedStatementSelect = connection.prepareStatement(query);) {
            preparedStatementSelect.setInt(1, subjectID);
            ResultSet resultSet = preparedStatementSelect.executeQuery();

            while (resultSet.next()) {
                int mark = resultSet.getInt("mark");
                System.out.println("average mark subject: " + subjectID + "mark:" + mark);
                return mark;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }

    public Map<Student, Integer> getAverageRating() {
        Map<Student, Integer> students = new HashMap<>();
        String query = "SELECT AVG(marks.mark) as mark,  " +
                " marks.student_id as id, " +
                " students.student_name as name, " +
                " students.group_id as group_id " +
                " FROM marks" +
                " LEFT JOIN students ON marks.student_id = students.student_id" +
                " GROUP BY marks.student_id;";

        try (Statement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Integer studentID = resultSet.getInt("id");
                String studentName = resultSet.getString("name");
                Integer mark = resultSet.getInt("mark");
                Integer groupId = resultSet.getInt("group_id");

                if (studentID != null && mark != null) {
                    Student student = findStudentById(studentID, studentName, groupId);
                    students.put(student, mark);
                    System.out.println("Student: " + studentName + ", average mark:" + mark);
                }
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Student findStudentById(Integer studentID, String studentName, Integer groupId) {
        Student student = new Student();
        student.setFirstName(studentName);
        student.setId(studentID);
        student.setGroupID(groupId);
        return student;
    }

    public int getAverageRating(int subjectID, int groupID) {
        String query = "SELECT AVG(marks.mark) as mark  " +
                " FROM marks" +
                " LEFT JOIN students ON marks.student_id = students.student_id" +
                " WHERE marks.subject_id = ? and students.group_id = ?;";

        try (PreparedStatement preparedStatementSelect = connection.prepareStatement(query);) {
            preparedStatementSelect.setInt(1, subjectID);
            preparedStatementSelect.setInt(2, groupID);
            ResultSet resultSet = preparedStatementSelect.executeQuery();

            while (resultSet.next()) {
                int mark = resultSet.getInt("mark");
                System.out.println("average mark subject: "+ subjectID + " , group: " + groupID + "mark:"+ mark);
                return mark;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        return 0;
    }
}
