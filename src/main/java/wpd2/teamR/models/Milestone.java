package wpd2.teamR.models;

import lombok.Data;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.util.Calendar;

@Data
public class Milestone {

    private int id;
    private String name;
    private String description;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private Timestamp dueDate;
    private Timestamp dateCompleted;

    private int projectID; // AGAIN, I DONT THINK WE NEED THIS TO BE HONEST ?

    public Milestone(int id, String name, String desc, Timestamp dateCreated, Timestamp dateMod, Timestamp dateDue, Timestamp dateComplete, int projectID)
    {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.dateCreated = dateCreated;
        this.dateModified = dateMod;
        this.dueDate = dateDue;
        this.dateCompleted = dateComplete;
    }

    public Milestone(String name, String desc, Timestamp dateMod, Timestamp dateDue, Timestamp dateComplete, int projectID)
    {
        this.name = name;
        this.description = desc;
        this.dateModified = dateMod;
        this.dueDate = dateDue;
        this.dateCompleted = dateComplete;
        this.projectID = projectID;
    }

    public Milestone() {

    }


    // THE BELOW GETTERS FACILITATE MUSTACHE
    public String getPrettyDateCreated(){
        PrettyTime p = new PrettyTime();
        return p.format(this.getDateCreated());
    }

    public String getPrettyDateModified(){
        PrettyTime p = new PrettyTime();
        return p.format(this.getDateModified());
    }


    public String getDueDay(){

        long timestamp = this.dueDate.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

    }

    public int getDueMonth(){

        long timestamp = this.dueDate.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        return cal.get(Calendar.MONTH);
    }

    public String getDueYear(){

        long timestamp = this.dueDate.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);

        return String.valueOf(cal.get(Calendar.YEAR));

    }

}
