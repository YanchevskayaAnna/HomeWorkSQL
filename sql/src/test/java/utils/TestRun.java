package utils;

import home.MySqlConstants;
import home.models.Group;
import home.models.Student;
import home.models.Subject;
import home.models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 29.09.16.
 */
public class TestRun {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(MySqlConstants.URL, MySqlConstants.USER, MySqlConstants.PASSWORD);
        //List<Student> studentList = getAllStudents(connection);
        //List<Group> groupList = getAllGroups(connection);
        //List<Subject> subjectList = getAllSubjects(connection);
        //List<Teacher> teacherList = getwALLTeachers(connection);

        //Student student = new Student("Ivan", "Ivanov");
        //student.setGroupID(2);
        //addStudent(student, connection);


    }

    private static boolean addStudent(Student student, Connection connection) {

        String query = "INSERT INTO students(student_name, student_last_name, group_id) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getFirsName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getGroupID());

            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<Teacher> getwALLTeachers(Connection connection) throws SQLException {
        System.out.println("TEACHERS");
        List<Teacher> teacherList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers;");

        while (resultSet.next()) {
            Teacher teacher = new Teacher(
                    resultSet.getString("teacher_name"),
                    resultSet.getString("teacher_last_name"),
                    resultSet.getDouble("teacher_exp"),
                    resultSet.getInt("subject_id"));
            teacherList.add(teacher);
            System.out.println(teacher);
        }
        return teacherList;
    }

    private static List<Subject> getAllSubjects(Connection connection) throws SQLException {
        System.out.println("SUBJECTS:");
        List<Subject> subjectList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects;");

        while (resultSet.next()) {
            Subject subject = new Subject(resultSet.getString("subject_title"), resultSet.getString("subject_desc"));
            subject.setId(resultSet.getInt("subject_id"));
            System.out.println(subject);
        }
        return subjectList;
    }

    private static List<Group> getAllGroups(Connection connection) throws SQLException {
        System.out.println("GROUPS:");
        List<Group> groupList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM groups;");

        while (resultSet.next()) {
            Group group = new Group(resultSet.getString("group_name"));
            group.setId(resultSet.getInt("group_id"));
            groupList.add(group);
            System.out.println(group);
        }
        return groupList;
    }

    public static List<Student> getAllStudents(Connection connection) throws SQLException {
        System.out.println("STUDENTS:");
        List<Student> studentList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students;");

        while (resultSet.next()) {
            Student student = new Student(resultSet.getString("student_name"), resultSet.getString("student_last_name"));
            student.setId(resultSet.getInt("student_id"));
            student.setGroupID(resultSet.getInt("group_id"));
            studentList.add(student);
            System.out.println(student);
        }
        return studentList;
    }


}
