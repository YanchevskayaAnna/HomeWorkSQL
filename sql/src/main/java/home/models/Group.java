package home.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 29.09.16.
 */
public class Group {

    private int id;
    private String name;
    private List<Student> studentList;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentList=" + studentList +
                '}';
    }

    public Group(String name) {
        this.name = name;
        this.id = -1;
        this.studentList = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
    }
}
