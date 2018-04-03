package wpd2.teamR.models;

import lombok.Data;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Data
public  class Project {

    private int id;
    private String name;
    private String description;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List<Milestone> milestones; // NOT SURE WHATS GOING ON HERE. MAKE THIS A HASHMAP TO RETRIEVE SINGLE Milestones? Dunno ¯\_(ツ)_/¯

    //private List<Milestone> milestones;  <= NAV VARIABLE / UNSURE

    public void addMilestone(Milestone milestoneToAdd)
    {
        //TODO: ADD BODY
        this.milestones.add(milestoneToAdd);
    }


    public Milestone getMilestone(int milestoneToGetId)
    {
        //TODO: ADD BODY
        //gets a milestone using the requested Id?
        return null; // <= THIS WILL CHANGE OBVIOUSLY
    }

    public void deleteMilestone(int milestoneToDeleteId)
    {
        //TODO: ADD BODY
        //Deletes a Milestone from a Project
    }

}
