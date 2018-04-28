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

public class MilestoneDispatcherServlet extends BaseServlet {

    static final Logger LOG = LoggerFactory.getLogger(ProjectListServlet.class);

    private MilestoneDAO milestones;

    public MilestoneDispatcherServlet(){ milestones = new MilestoneDAO();}


    protected void  doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        // SETUP PLACEHOLDER FOR THE PROJECT ID
        int projectID;

        // GET THE PROJECT ID - IF NOT NUMBER, SOMEONE IS TAMPERING, REDIRECT THEM WITH ERROR
        try {

            projectID = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        } catch(NumberFormatException error){

            // UH OH...YOU AINT HACKING MY APP...
            SessionFunctions.setFlashMessage(request,new FlashMessage(FlashMessage.FlashType.ERROR,"Uh oh..","Something went wrong with what you were trying to access."));
            response.sendRedirect("/projects");
            return;

        }

        // UPDATE THE SESSION VARIABLE AND REDIRECT TO THE ACTUAL MILESTONE VIEW
        setCurrentProject(request,projectID);

        SessionFunctions.setFlashMessage(request,new FlashMessage(FlashMessage.FlashType.INFO,"WROTE TO THE SESSION","SESSION ID WAS WRITTEn - PROJECT IS "+projectID));
        response.sendRedirect("/milestones");

    }

}
