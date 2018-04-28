package wpd2.teamR.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.dao.MilestoneDAO;
import wpd2.teamR.dao.ProjectDAO;
import wpd2.teamR.models.Project;
import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProjectListServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ProjectListServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private ProjectDAO projects;
    private MilestoneDAO milestones;


    public ProjectListServlet() {
        projects = new ProjectDAO();
        milestones = new MilestoneDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        List<Project> plist = new ArrayList<Project>();
        try {
            plist = projects.getProjectsbyUser(getCurrentUser(request));

            plist.forEach((v) -> {
                try {
                    v.setMilestones(milestones.getAllMilestonesByProjectId(v.getId()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException error) {
            // TODO: SOMETHING WENT WRONG - GENERATE ERROR
        }

        HashMap<String, Object> viewBag = new HashMap<String, Object>();

        FlashMessage message = SessionFunctions.getFlashMessage(request);
//        viewBag.put("username",userName);
        viewBag.put("message", message);
        viewBag.put("total", plist.size());
        viewBag.put("projects", plist);

        showView(response, "project/project-list.mustache", viewBag);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Project p = new Project();
        p.setName(request.getParameter("name"));
        p.setDescription(request.getParameter("description"));

        if (projects.createProject(p, getCurrentUser(request))) {

            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Project Added", "Your project was added"));
            response.sendRedirect("/private");
            return;

        }

    }


}
