package dao;

import model.Group;
import model.Subject;

import java.sql.SQLException;
import java.util.List;

public interface GroupDAO extends AbstractDAO<Group>{
    public List<Group> getAllGroupsWithSubject(Subject subject) throws SQLException;
}
