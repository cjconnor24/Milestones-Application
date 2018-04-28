

package wpd2.teamR.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wpd2.teamR.dao.ProjectDAO;

import wpd2.teamR.models.Project;

import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class ProjectDeleteServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ProjectCreateServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private ProjectDAO projects;

    public ProjectDeleteServlet() {
        projects = new ProjectDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //IS THIS NEEDED HERE?????
        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }
        String email = getCurrentUser(request);

        //GETTING ID FROM URL
        int id = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        Project projectToDelete = null;

        //GETTING PROJECT TO BE DELETED
        try {
            projectToDelete = projects.getProjectByIdAndUser(id, email);
        } catch (SQLException e) {
            returnNotFound(request, response);
        }

        if (projectToDelete != null) {
            HashMap<String, Object> viewBag = new HashMap<String, Object>();

            FlashMessage message = SessionFunctions.getFlashMessage(request);
            viewBag.put("project", projectToDelete);
            viewBag.put("message", message);

            showView(response, "project/project-delete.mustache", viewBag);
        } else {
            returnNotFound(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //IS THIS NEEDED HERE?????
        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        //GETTING ID FROM URL
        int parameter = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        String typedEmail = request.getParameter("email");
        String email = getCurrentUser(request);

        if (!email.equals(typedEmail)) {

            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "No match", "The email wasn't correct. Please try again."));
            response.sendRedirect("/projects/delete/" + parameter);
            return;

        }

        //CHECKS IF DELETED OR NOT AND RETURNS CORRECT RESPONSE
        if (projects.deleteProjectById(parameter)) {
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Project Deleted", "The project was deleted"));
        } else {
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Project Could Not Be Deleted", "The project was not deleted!"));
        }

        response.sendRedirect("/projects");
        return;

    }


    private void returnNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Project Could Not Be Found", "The project was not found, please refresh system!"));
        response.sendRedirect("/projects");
        return;
    }

}



