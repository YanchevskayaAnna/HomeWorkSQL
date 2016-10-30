package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sf on 03.10.16.
 */
public class Learning {

    private int groupID;
    private List<Integer> subjectsIDs;

    public Learning() {
        this.subjectsIDs = new ArrayList<>();
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public List<Integer> getSubjects() {
        return subjectsIDs;
    }

    public void setSubjects(List<Integer> subjects) {

        this.subjectsIDs = subjects;
    }

    public boolean addSub(Integer sub){
        subjectsIDs.add(sub);
        return true;
    }
}
