package wpd2.teamR.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Link {

    private int id;
    private String email;
    private String urlHash;
    private Timestamp dateCreated;
    private Timestamp dateLastAccessed;
    private int projectID;
    private Project project;

    public Link(int id, String email, Timestamp dateCreated, Timestamp dateLastAccessed) {
        this.id = id;
        this.email = email;
        this.dateCreated = dateCreated;
        this.dateLastAccessed = dateLastAccessed;
    }

    public Link(int id, String email, Timestamp dateCreated, Timestamp dateLastAccessed, int projectID) {
        this.id = id;
        this.email = email;
        this.dateCreated = dateCreated;
        this.dateLastAccessed = dateLastAccessed;
        this.projectID = projectID;
    }

    public Link(int id, String email, Timestamp dateCreated, Timestamp dateLastAccessed, int projectID, String urlHash) {
        this.id = id;
        this.email = email;
        this.dateCreated = dateCreated;
        this.dateLastAccessed = dateLastAccessed;
        this.projectID = projectID;
        this.urlHash = urlHash;
    }

    public Link(String email, Timestamp dateCreated, Timestamp dateLastAccessed) {
        this.email = email;
        this.dateCreated = dateCreated;
        this.dateLastAccessed = dateLastAccessed;
    }

    public Link(String email, int projectID) {
        this.email = email;
        this.projectID = projectID;
    }

    public Link(String email) {
        this.email = email;
    }

}