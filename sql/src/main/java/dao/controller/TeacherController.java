package dao.controller;

import dao.MyAbstractController;
import dao.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public class TeacherController implements MyAbstractController<Teacher, Integer> {

    private Connection connection;

    public TeacherController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Teacher> getAll() {

        List<Teacher> teachers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers;")) {

            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setFirstName(resultSet.getString("first_name"));
                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setExp(resultSet.getDouble("exp"));
                teacher.setSubjectId(resultSet.getInt("subject_id"));
                teacher.setId(resultSet.getInt("id"));
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Teacher getEntityById(Integer id) {

        Teacher teacher = new Teacher();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers WHERE id=" + id + ";")) {

            if(resultSet.next()) {
                teacher.setFirstName(resultSet.getString("first_name"));
                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setExp(resultSet.getDouble("exp"));
                teacher.setSubjectId(resultSet.getInt("subject_id"));
                teacher.setId(resultSet.getInt("id"));
                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean update(Teacher entity) {

        String sqlQuery = "UPDATE teachers SET first_name=?, last_name=?, exp=?, subject_id=? WHERE id=?";

        if(getEntityById(entity.getId())==null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(3, entity.getExp());
            preparedStatement.setInt(4, entity.getSubjectId());
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean create(Teacher entity) {

        String sqlQuery = "INSERT INTO teachers(first_name, last_name, exp, subject_id) VALUES (?,?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(3, entity.getExp());
            preparedStatement.setInt(4, entity.getSubjectId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
