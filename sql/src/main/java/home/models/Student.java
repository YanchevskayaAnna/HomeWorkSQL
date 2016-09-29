package home.models;

/**
 * Created by sf on 29.09.16.
 */
public class Student {

    private int id;
    private String firsName;
    private String lastName;
    private int groupID;

    public Student(String name, String lastName) {
        this.firsName = name;
        this.lastName = lastName;
        this.groupID = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
