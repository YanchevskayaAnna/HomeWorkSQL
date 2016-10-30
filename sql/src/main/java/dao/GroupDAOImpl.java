package dao;

import model.Group;
import model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 08.10.2016.
 */
public class GroupDAOImpl implements GroupDAO {

    private Connection connection;

    public GroupDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Group> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups;");) {

            List<Group> groupList = getAllGroupsFromResultSet(resultSet);

            return groupList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Group getEntityById(Integer id) {
        Group group = new Group();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups WHERE group_id=" + id + ";")) {

            if (resultSet.next()) {
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean update(Group entity) {
        String sqlQuery = "UPDATE groups SET group_name=? WHERE group_id=?";

        if (getEntityById(entity.getId()) == null) {
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean create(Group entity) {
        String sqlQuery = "INSERT INTO groups(group_name) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public List<Group> getAllGroupsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Group> groupList = new ArrayList<>();
        while (resultSet.next()) {
            Group group = new Group();
            group.setId(resultSet.getInt("group_id"));
            group.setName(resultSet.getString("group_name"));
            groupList.add(group);
        }
        return groupList;
    }

    public List<Group> getAllGroupsWithSubject(Subject subject) throws SQLException {

        String sqlQuery = "SELECT groups.group_id id, groups.group_name, subjects.subject_id, subjects.subject_title FROM learning LEFT JOIN subjects " +
                "ON learning.subject_id = subjects.subject_id RIGHT JOIN groups ON groups.group_id=learning.group_id where subject_title='" +
                subject.getName() + "';";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery);) {

            List<Group> groupList = getAllGroupsFromResultSet(resultSet);

            return groupList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


}
