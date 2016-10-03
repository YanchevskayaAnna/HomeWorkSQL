package dao.controller;

import dao.MyAbstractController;
import dao.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public class StudentController implements MyAbstractController<Student, Integer> {

    private Connection connection;

    public StudentController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> getAll() {

        List<Student> students = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students;");) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setId(resultSet.getInt("id"));
                student.setGroupID(resultSet.getInt("group_id"));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Student getEntityById(Integer id) {
        Student student = new Student();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE id=" + id + ";")) {

            if(resultSet.next()) {
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setId(resultSet.getInt("id"));
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

        String sqlQuery = "UPDATE students SET first_name=?, last_name=?, group_id=? WHERE id=?";

        if(getEntityById(entity.getId())==null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getGroupID());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }


    @Override
    public boolean create(Student entity) {
        String sqlQuery = "INSERT INTO students(first_name, last_name, group_id) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getGroupID());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
