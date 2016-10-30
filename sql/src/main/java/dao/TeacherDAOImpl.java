package dao;

import model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {
    private Connection connection;

    public TeacherDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Teacher> getAll() {

        List<Teacher> teachers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers;")) {

            teachers = getTeachersFromResultSet(resultSet);
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers WHERE teacher_id=" + id + ";")) {

            if (resultSet.next()) {
//                teacher.setFirstName(resultSet.getString("first_name"));
//                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setLastName(resultSet.getString("teacher_name"));
                teacher.setExp(resultSet.getDouble("teacher_exp"));
                teacher.setSubjectId(resultSet.getInt("subject_id"));
                teacher.setId(resultSet.getInt("teacher_id"));
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

//        String sqlQuery = "UPDATE teachers SET first_name=?, last_name=?, exp=?, subject_id=? WHERE id=?";
        String sqlQuery = "UPDATE teachers SET teacher_name=?, teacher_exp=?, subject_id=? WHERE teacher_id=?";

        if (getEntityById(entity.getId()) == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getFirstName());
//            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(2, entity.getExp());
            preparedStatement.setInt(3, entity.getSubjectId());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean create(Teacher entity) {

//        String sqlQuery = "INSERT INTO teachers(first_name, last_name, exp, subject_id) VALUES (?,?,?,?);";
        String sqlQuery = "INSERT INTO teachers(teacher_name, teacher_exp, subject_id) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, entity.getFirstName());
//            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(2, entity.getExp());
            preparedStatement.setInt(3, entity.getSubjectId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Teacher getTeacherWithMinExp() {

        List<Teacher> teacherList = getAll();
        Teacher teacherWithMinExp = new Teacher();
        teacherWithMinExp.setExp(100);
        for (Teacher teacher : teacherList) {
            if (teacherWithMinExp.getExp() > teacher.getExp()) {
                teacherWithMinExp = teacher;
            }
        }

        return teacherWithMinExp;
    }

    public Teacher getTeacherWithMaxExp() {

        List<Teacher> teacherList = getAll();
        Teacher teacherWithMaxExp = new Teacher();
        teacherWithMaxExp.setExp(0);
        for (Teacher teacher : teacherList) {
            if (teacherWithMaxExp.getExp() < teacher.getExp()) {
                teacherWithMaxExp = teacher;
            }
        }

        return teacherWithMaxExp;
    }

    public List<Teacher> getTeachersWithExp(double exp) {

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers WHERE teacher_exp>=" + exp + ";")) {
            List<Teacher> teachers = getTeachersFromResultSet(resultSet);
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Teacher> getTeachersFromResultSet(ResultSet resultSet) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        while (resultSet.next()) {
            Teacher teacher = new Teacher();
            teacher.setFirstName(resultSet.getString("teacher_name"));
//            teacher.setLastName(resultSet.getString("last_name"));
            teacher.setExp(resultSet.getDouble("teacher_exp"));
            teacher.setSubjectId(resultSet.getInt("subject_id"));
            teacher.setId(resultSet.getInt("teacher_id"));
            teachers.add(teacher);
        }
        return teachers;
    }

}
