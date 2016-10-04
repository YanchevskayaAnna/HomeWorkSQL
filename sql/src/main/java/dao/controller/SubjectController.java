package dao.controller;

import dao.MyAbstractController;
import dao.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public class SubjectController implements MyAbstractController<Subject, Integer> {

    private Connection connection;

    public SubjectController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subject> getAll() {

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects;");) {

            List<Subject> subjects = getFromResultSet(resultSet);
            return subjects;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Subject getEntityById(Integer id) {

        Subject subject = new Subject();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE id=" + id + ";")) {

            if (resultSet.next()) {
                subject.setId(resultSet.getInt("id"));
                subject.setName(resultSet.getString("title"));
                subject.setDescription(resultSet.getString("description"));
                return subject;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean update(Subject entity) {
        String sqlQuery = "UPDATE subjects SET title=?, description=? WHERE id=?";

        if (getEntityById(entity.getId()) == null) {
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Subject entity) {
        String sqlQuery = "INSERT INTO subjects(title, description) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Subject> getHumSubjects() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE description='HUM';")) {
            List<Subject> subjects = getFromResultSet(resultSet);
            return subjects;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Subject> getFromResultSet(ResultSet resultSet) throws SQLException {
        List<Subject> subjectList = new ArrayList<>();
        while (resultSet.next()) {
            Subject subject = new Subject();
            subject.setId(resultSet.getInt("id"));
            subject.setName(resultSet.getString("title"));
            subject.setDescription(resultSet.getString("description"));
            subjectList.add(subject);
        }

        return subjectList;
    }

}
