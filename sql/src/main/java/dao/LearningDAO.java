package dao;

import model.Group;
import model.Learning;
import model.Student;
import model.Subject;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LearningDAO extends AbstractDAO<Learning> {

    public Map<Student, Integer> getAverageRating();

    public int getAverageRatingGroup (int groupID);

    public int getAverageRating(int subjectID);

    public int getAverageRating(int subjectID, int groupID);
}
