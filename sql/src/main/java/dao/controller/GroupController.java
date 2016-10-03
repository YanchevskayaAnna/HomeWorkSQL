package dao.controller;

import dao.MyAbstractController;
import dao.model.Group;

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
        List<Group> groupList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM groups;");) {

            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                groupList.add(group);
            }
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

}
