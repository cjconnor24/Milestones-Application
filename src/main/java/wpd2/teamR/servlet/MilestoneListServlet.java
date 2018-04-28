package wpd2.teamR.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.dao.MilestoneDAO;
import wpd2.teamR.models.Milestone;
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

public class MilestoneListServlet extends BaseServlet {

    static final Logger LOG = LoggerFactory.getLogger(ProjectListServlet.class);

    private MilestoneDAO milestones;

    public MilestoneListServlet() {
        milestones = new MilestoneDAO();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

//        int parameter = Integer.parseInt(request.getParameter("Identifier"));
//         setCurrentProject(request,parameter);
        int projectID = getCurrentProject(request);
        if (projectID == -1) {
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.INFO, "Hmmm something isn't right", "Please select the project again."));
            response.sendRedirect("/projects");
            return;
        }

        List<Milestone> milestoneList = new ArrayList<>();
//        try {
        try {
            milestoneList = milestones.getAllMilestonesByProjectId(projectID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        }
//        catch (SQLException error){}

        HashMap<String, Object> viewBag = new HashMap<String, Object>();

        FlashMessage message = SessionFunctions.getFlashMessage(request);
        viewBag.put("message", message);
        viewBag.put("projectId", projectID);
        viewBag.put("total", milestoneList.size());
        viewBag.put("milestones", milestoneList);

        showView(response, "milestone/milestone-list.mustache", viewBag);

    }


}
