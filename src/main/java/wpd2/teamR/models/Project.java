package wpd2.teamR.models;

import lombok.Data;
import org.ocpsoft.prettytime.PrettyTime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class Project {

    private int id;
    private String name;
    private String description;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private List<Milestone> milestones; // NOT SURE WHATS GOING ON HERE. MAKE THIS A HASHMAP TO RETRIEVE SINGLE Milestones? Dunno ¯\_(ツ)_/¯

    //private List<Milestone> milestones;  <= NAV VARIABLE / UNSURE

    public Project() {
    }

    public Project(int id, String name, String description, Timestamp dateCreated, Timestamp dateModified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * Return human readable Datetime Created
     * @return Human readable datetime string
     */
    public String getPrettyDateCreated(){
        PrettyTime p = new PrettyTime();
//        return p.format(this.getDateCreated());

            return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(this.getDateCreated());


    }

    /**
     * Return human readable Datetime Modified
     * @return Human readable datetime string
     */
    public String getPrettyDateModified(){
        PrettyTime p = new PrettyTime();
        return p.format(this.getDateModified());
    }



}
