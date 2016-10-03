package dao.controller;

import dao.MyAbstractController;
import dao.model.Learning;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sf on 03.10.16.
 */
public class LearningController implements MyAbstractController<Learning, Integer> {

    private Connection connection;

    public LearningController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Learning> getAll() {
        List<Learning> learningList = new ArrayList<>();
        Set<Integer> groupsSet = new HashSet<>();
        String query = "SELECT groups.id gid, subjects.id sid FROM groups LEFT JOIN learning ON" +
                " groups.id=learning.group_id LEFT JOIN subjects ON learning.subject_id=subjects.id;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Integer groupID = resultSet.getInt("gid");
                Integer subjectID = resultSet.getInt("sid");
                if(groupID!= null && subjectID!=null) {
                    if (!groupsSet.contains(groupID)) {
                        Learning learning = new Learning();
                        learning.setGroupID(groupID);
                        learning.addSub(subjectID);
                        learningList.add(learning);
                        groupsSet.add(learning.getGroupID());
                    } else {
                        Learning learning = findLearningById(learningList, groupID);
                        learning.addSub(subjectID);
                    }
                }
            }
            return learningList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Learning getEntityById(Integer id) {

        Learning learning = new Learning();
        String query = "SELECT groups.id gid, subjects.id sid FROM groups LEFT JOIN learning ON" +
                " groups.id=learning.group_id LEFT JOIN subjects ON learning.subject_id=subjects.id " +
                "WHERE groups.id=" + id + ";";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if(resultSet.next()) {
                learning.setGroupID(resultSet.getInt("gid"));
                learning.addSub(resultSet.getInt("sid"));
            }

            while(resultSet.next()) {
                learning.addSub(resultSet.getInt("sid"));
            }
            return learning;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Learning entity) {
        String sqlQueryForDel = "DELETE FROM learning WHERE group_id=?;";
        String sqlQueryForAdd = "INSERT INTO learning (group_id, subject_id) VALUES (?,?)";

        try (PreparedStatement preparedStatementDel = connection.prepareStatement(sqlQueryForDel);
             PreparedStatement preparedStatementAdd = connection.prepareStatement(sqlQueryForAdd);){
            preparedStatementDel.setInt(1, entity.getGroupID());
            preparedStatementDel.execute();

            for(Integer integer : entity.getSubjects()) {
                preparedStatementAdd.setInt(1, entity.getGroupID());
                preparedStatementAdd.setInt(2, integer);
                preparedStatementAdd.execute();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean create(Learning entity) {
        return update(entity);
    }

    private static Learning findLearningById(List<Learning> learnings, int id) {
        for (Learning learning : learnings) {
            if(learning.getGroupID()==id) {
                return learning;
            }
        }
        return null;
    }

}
