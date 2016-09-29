package home.models;

/**
 * Created by sf on 29.09.16.
 */
public class Group {

    private int id;
    private String name;

    public Group(String name) {
        this.name = name;
        this.id = -1;
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
}
