package wpd2.teamR.dao;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.models.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProjectDAO extends DAOBase {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectDAO.class);

    public ProjectDAO() {
        // CALL THE DAO BASE TO INITIALISE THE DB CONNCTION
        super();

    }

    //region Read

    /**
     * Gets a single project with the specified id
     *
     * @param id of project to get
     * @return Project object of the recieved project
     * @throws SQLException
     */
    public Project getProjectById(int id) throws SQLException {

        final String GET_PROJECT = "SELECT * FROM projects WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(GET_PROJECT)) {

            // PASS THROUGH THE id INTO THE PREPARED STATEMENT
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            return (addSingleProject(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a project where the Id and userId match
     *
     * @param id    int variable of project id
     * @param email String variable of users email address
     * @return Project Object or Null depending if found or not
     * @throws SQLException
     */
    public Project getProjectByIdAndUser(int id, String email) throws SQLException {
        final String GET_PROJECT = "SELECT * FROM projects WHERE id=? AND userID = (SELECT id FROM users WHERE email = ?)";

        try (PreparedStatement ps = connection.prepareStatement(GET_PROJECT)) {

            // PASS THROUGH THE id INTO THE PREPARED STATEMENT
            ps.setInt(1, id);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();

            return addSingleProject(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a list of all projects from the database
     *
     * @return List of projects that contains all database projects
     * @throws SQLException
     */
    public List<Project> getAllProjects() throws SQLException {

        final String GET_PROJECTS = "SELECT * FROM projects";

        try (PreparedStatement ps = connection.prepareStatement(GET_PROJECTS)) {

            ResultSet rs = ps.executeQuery();

            return addListProjects(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets all projects for a single user
     *
     * @param email address of user to recieve projects for
     * @return List of projects that contains all of a single users projects
     * @throws SQLException
     */
    public List<Project> getProjectsbyUser(String email) throws SQLException {

        final String GET_USERS_PROJECTS = "SELECT * FROM projects WHERE userID = (SELECT id FROM users WHERE email = ?)";

        try (PreparedStatement ps = connection.prepareStatement(GET_USERS_PROJECTS)) {

            // PASS THROUGH THE EMAIL INTO THE PREPARED STATEMENT
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return addListProjects(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //endregion

    //region Create

    /**
     * Creates a project and stores in the database
     *
     * @param project object containing name and description details to be written
     * @param Email   address of user to asign the project to
     * @return boolean of successfull or not
     */
    public boolean createProject(Project project, String Email) {

        String query = "INSERT INTO projects (name, description, dateCreated, dateModified, userID) VALUES(?,?,NOW(),NOW(),(SELECT id FROM users WHERE email = ?))";

        try (PreparedStatement ps = getConnection().prepareStatement(query)) {

            //PASS VARIABLES TO PREPARED STATEMENT
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setString(3, Email);
            int count = ps.executeUpdate();
            LOG.debug("insert count = " + count);

            return determineTrueFalse(count);

        } catch (SQLException error) {
            LOG.debug(error.toString());
            return false;
        }

    }
    //endregion

    //region Delete

    /**
     * Deletes a project from the database with the matching id
     *
     * @param id of project to be deleted
     * @return boolean of sucessfull or not
     */
    public boolean deleteProjectById(int id) {

        String query = "DELETE FROM projects WHERE id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(query)) {

            //PASS ID TO PREPARED STATEMENT
            ps.setInt(1, id);

            int count = ps.executeUpdate();
            LOG.debug("insert count = " + count);

            return determineTrueFalse(count);

        } catch (SQLException error) {
            LOG.debug(error.toString());
            return false;
        }

    }
    //endregion

    //region Update //TODO

    /**
     * Updates a project that is stored in the database
     *
     * @param project object containing the updated name and description details to be written
     * @return boolean of successfull or not
     */
    public boolean projectUpdate(Project project) {

        String query = "UPDATE projects SET name=?, description=?, dateModified=NOW() WHERE id = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(query)) {

            //PASS VARIABLES TO PREPARED STATEMENT
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setInt(3, project.getId());
            int count = ps.executeUpdate();
            LOG.debug("insert count = " + count);

            return determineTrueFalse(count);

        } catch (SQLException error) {
            LOG.debug(error.toString());
            return false;
        }

    }


    //endregion

    //region Private Methods


    /**
     * Private method that creates a list of projects from a passed in ResultSet
     *
     * @param rs Resultset containg raw projects
     * @return List<Project> variable containing a list of projects
     * @throws SQLException
     */
    private List<Project> addListProjects(ResultSet rs) throws SQLException {

        // LOOP THROUGH RESULTS
        List<Project> projectList = new ArrayList<Project>();
        while (rs.next()) {
            
            //ADD NEW PROJECT WITH CURRENT RESULTSET DETAILS
            projectList.add(new Project(rs.getInt("id"), rs.getString("name")
                    , rs.getString("description"), rs.getTimestamp("dateCreated"), rs.getTimestamp("dateModified")));
        }

        return projectList;
    }

    /**
     * Private method that returns true of false depending on int value
     * Used to cut down repetitive code
     *
     * @param count Integer variable to be used to determine true or false
     * @return Boolean depending on count value
     */
    private boolean determineTrueFalse(int count) {
        //RETURBNS TRUE OR FALSE DEPENDING ON COUNT RESULT
        if (count == 1) {

            return true;

        } else {

            return false;

        }
    }

    /**
     * Creates a single Project object and returns it
     *
     * @param rs Resultset containg raw project
     * @return Project object constructed from the resultset
     * @throws SQLException
     */
    private Project addSingleProject(ResultSet rs) throws SQLException {
        // LOOP THROUGH RESULTS - SHOULD ONLY BE ONE
        Project project = null;
        while (rs.next()) {
            //ADD NEW PROJECT WITH CURRENT RESULTSET DETAILS
            project = new Project(rs.getInt("id"), rs.getString("name")
                    , rs.getString("description"), rs.getTimestamp("dateCreated"), rs.getTimestamp("dateModified"));
        }

        return project;
    }

    //endregion

}


