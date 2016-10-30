package service;

import dao.*;
import model.Group;
import model.Learning;
import model.Subject;
import model.Teacher;
import model.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class AdminController {

    private GroupDAO groupDAO;
    private LearningDAO learningDAO;
    private SubjectDAO subjectDAO;
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;

    public AdminController(GroupDAO groupDAO, LearningDAO learningDAO, SubjectDAO subjectDAO, TeacherDAO teacherDAO, StudentDAO studentDAO) {
        this.groupDAO = groupDAO;
        this.learningDAO = learningDAO;
        this.subjectDAO = subjectDAO;
        this.teacherDAO = teacherDAO;
        this.studentDAO = studentDAO;
    }

    //TEACHERS
    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAll();
    }

    public Teacher getTeacherById(Integer id) {
        return teacherDAO.getEntityById(id);
    }

    public boolean updateTeacher(Teacher teacher) {
        return teacherDAO.update(teacher);
    }

    public boolean createTeacher(Teacher teacher) {
        return teacherDAO.create(teacher);
    }

    public boolean deleteTeacher(String tableName, int id) {
//       return teacherDAO.delete(String tableName, int id);
        return false;
    }

    //SUBJECTS
    public List<Subject> getAllSubjects() {
        return subjectDAO.getAll();
    }

    public Subject getSubjectById(Integer id) {
        return subjectDAO.getEntityById(id);
    }

    public boolean updateSubject(Subject subject) {
        return subjectDAO.update(subject);
    }

    public boolean createSubject(Subject subject) {
        return subjectDAO.create(subject);
    }

    public boolean deleteSubject(String tableName, int id) {
        return false;
    }

    //Learning
    public List<Learning> getAllLearning() {
        return learningDAO.getAll();
    }

    public Learning getLearningById(Integer id) {
        return learningDAO.getEntityById(id);
    }

    public boolean updateLearning(Learning learning) {
        return learningDAO.update(learning);
    }

    public boolean createLearning(Learning learning) {
        return learningDAO.create(learning);
    }

    public boolean deleteLearning(String tableName, int id) {
        return false;
    }

    //STUDENTS
    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    public Student getStudentById(Integer id) {
        return studentDAO.getEntityById(id);
    }

    public boolean updateStudent(Student student) {
        return studentDAO.update(student);
    }

    public boolean createStudent(Student student) {
        return studentDAO.create(student);
    }

    public boolean deleteStudent(String tableName, int id) {
        return false;
    }

    public List<Group> getAllGroupsWithSubject(Subject subject) throws SQLException {
        return groupDAO.getAllGroupsWithSubject(subject);
    }


    //GROUP
    public List<Group> getAllGroups() {
        return groupDAO.getAll();
    }

    public Group getGroupById(Integer id) {
        return groupDAO.getEntityById(id);
    }

    public boolean updateGroup(Group group) {
        return groupDAO.update(group);
    }

    public boolean createGroup(Group group) {
        return groupDAO.create(group);
    }

    public boolean deleteGroup(String tableName, int id) {
        return false;
    }

    //    -узнать средний бал студентов по физике (всех и определенной группы)
    public int getAverageRating(int subjectID) throws SQLException {

        return learningDAO.getAverageRating(subjectID);
    }

    public int getAverageRating(int subjectID, int groupID) {
        return learningDAO.getAverageRating(subjectID, groupID);
    }

    public int getAverageRatingGroup(int groupID) {
        return learningDAO.getAverageRatingGroup(groupID);
    }
//    -показать группу, в которой более 3-х студентов изучают философию (и выгнать с универа)

    public Map<Student, Integer> getAverageRating(){
        return learningDAO.getAverageRating();
}

}
