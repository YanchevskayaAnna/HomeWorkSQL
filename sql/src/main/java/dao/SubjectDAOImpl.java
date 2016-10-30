package dao;

import model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 08.10.2016.
 */
public class SubjectDAOImpl implements SubjectDAO {

    private Connection connection;

    public SubjectDAOImpl(Connection connection) {
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE subject_id=" + id + ";")) {

            if (resultSet.next()) {
                subject.setId(resultSet.getInt("subject_id"));
                subject.setName(resultSet.getString("subject_title"));
                subject.setDescription(resultSet.getString("subject_desc"));
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
        String sqlQuery = "UPDATE subjects SET subject_title=?, subject_desc=? WHERE subject_id=?";

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
        String sqlQuery = "INSERT INTO subjects(subject_title, subject_desc) VALUES (?,?)";

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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE subject_desc='HUM';")) {
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
            subject.setId(resultSet.getInt("subject_id"));
            subject.setName(resultSet.getString("subject_title"));
            subject.setDescription(resultSet.getString("subject_desc"));
            subjectList.add(subject);
        }

        return subjectList;
    }
}
