package dao.controller;

import dao.MyAbstractController;
import dao.model.Group;
import dao.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public class GroupController implements MyAbstractController<Group, Integer> {

    private Connection connection;

    public GroupController(Connection connection) {
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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups WHERE id=" + id + ";")) {

            if (resultSet.next()) {
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
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
        String sqlQuery = "UPDATE groups SET name=? WHERE id=?";

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
        String sqlQuery = "INSERT INTO groups(name) VALUES (?)";

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
            group.setId(resultSet.getInt("id"));
            group.setName(resultSet.getString("name"));
            groupList.add(group);
        }
        return groupList;
    }

    public List<Group> getAllGroupsWithSubject(Subject subject) throws SQLException {

        String sqlQuery = "SELECT group_id id, groups.name, subject_id, subjects.title FROM learning LEFT JOIN subjects " +
                "ON learning.subject_id=subjects.id RIGHT JOIN groups ON groups.id=learning.group_id where title='" +
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
