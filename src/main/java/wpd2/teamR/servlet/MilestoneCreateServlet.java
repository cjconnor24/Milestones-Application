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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class MilestoneCreateServlet extends BaseServlet {

    static final Logger LOG = LoggerFactory.getLogger(ProjectCreateServlet.class);

    //Connection to DAO
    private MilestoneDAO milestones;

    /**
     * Initialise servlet and get a copy of all milestones
     */
    public MilestoneCreateServlet() {
        milestones = new MilestoneDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        // SETUP VIEWBAG TO SEND TO VIEW
        HashMap<String, Object> viewBag = new HashMap<String, Object>();
        FlashMessage message = SessionFunctions.getFlashMessage(request);
        viewBag.put("mode", "Create");
        viewBag.put("message", message);

        // RENDER CREATE FORM
        showView(response, "milestone/milestone-create.mustache", viewBag);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // BUILD THE NEW MILESTONE - TODO: SERVER SIDE VALIDATION - jQuery / html catching for now.


        int projectId = getCurrentProject(request);

        Date date = convertDate(request);

        Milestone m = new Milestone();

        m.setName(request.getParameter("name"));
        m.setDescription(request.getParameter("description"));
        m.setDueDate(new Timestamp(date.getTime()));
        m.setProjectID(projectId);


        // IF IT WAS SUCCESSFULLY CREATED
        if (milestones.createMilestone(m, m.getProjectID())) {

            // SAVE A SUCCESSFUL FLASH MESSAGE AND RETURN TO PROJECT VIEW
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Milestone Added", "Your milestone was added to the project"));
            response.sendRedirect("/milestones");
            return;

        } else {

            // SOMETHING WENT WRONG - SEND THEM BACK TO FORM WITH ERROR
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Uh oh...", "Sorry, something went wrong"));
            response.sendRedirect("/milestones/create");
            return;
        }

    }

    private Date convertDate(HttpServletRequest request) {
        String datetimeString = request.getParameter("dueDate");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        try {
            date = sdf.parse(datetimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


}
