package home.models;

/**
 * Created by sf on 29.09.16.
 */
public class Teacher {

    private int id;
    private String firstName;
    private String lastName;
    private double exp;
    private int subjectId;

    public Teacher(String name, String lastName, double exp, int subjectId) {
        this.firstName = name;
        this.lastName = lastName;
        this.exp = exp;
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
