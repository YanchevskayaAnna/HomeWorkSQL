package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Connection connection;

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> getAll() {

        return getAllStudentsWithCondition(null);
    }

    @Override
    public Student getEntityById(Integer id) {
        Student student = new Student();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE student_id=" + id + ";")) {

            if (resultSet.next()) {
//                student.setFirstName(resultSet.getString("first_name"));
//                student.setLastName(resultSet.getString("last_name"));
                student.setFirstName(resultSet.getString("student_name"));
                student.setLastName(resultSet.getString("student_name"));
                student.setId(resultSet.getInt("student_id"));
                student.setGroupID(resultSet.getInt("group_id"));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean update(Student entity) {

//        String sqlQuery = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE id=?";
        String sqlQuery = "UPDATE students SET student_name=?, group_id=? WHERE student_id=?";

        if (getEntityById(entity.getId()) == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getFirstName());
//            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(2, entity.getGroupID());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


    @Override
    public boolean create(Student entity) {
        String sqlQuery = "INSERT INTO students(student_name, group_id) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, entity.getFirstName());
//            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(2, entity.getGroupID());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Student> getAllStudentsWithCondition(String condition) {

        List<Student> students = new ArrayList<>();
        String sqlQuery = "SELECT * FROM students";
        if (condition != null) {
            sqlQuery += " WHERE " + condition + ";";
        } else {
            sqlQuery+=";";
        }
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setFirstName(resultSet.getString("student_name"));
                student.setLastName(resultSet.getString("student_name"));
                student.setId(resultSet.getInt("student_id"));
                student.setGroupID(resultSet.getInt("group_id"));
                students.add(student);
            }

            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
