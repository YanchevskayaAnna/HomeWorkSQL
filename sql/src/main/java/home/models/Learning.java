package home.models;

/**
 * Created by sf on 29.09.16.
 */
public class Learning {

    private int groupID;
    private int subjectID;

    public Learning(int subjectID) {
        this.groupID = -1;
        this.subjectID = subjectID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }
}
