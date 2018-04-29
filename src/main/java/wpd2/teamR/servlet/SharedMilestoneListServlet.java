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
import java.util.Map;

/**
 * THIS SHARED MILESTONE SERVLET EXTENDS SHARED LOGIN INSTEAD OF BASE SERVLET
 */
public class SharedMilestoneListServlet extends SharedLoginServlet {

    static final Logger LOG = LoggerFactory.getLogger(ProjectListServlet.class);

    private MilestoneDAO milestones;

    public SharedMilestoneListServlet() {
        super();
        milestones = new MilestoneDAO();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO: RESTRICT TO ONLY GUEST SESSIONS
        if (!guestAuthOK(request, response)) {
            return;
        }

        Map<String, String> guestSession = getGuestSession(request);
        List<Milestone> milestoneList = null;
        try {
            milestoneList = this.milestones.getGuestMilestones(guestSession.get(this.GUEST_KEY), guestSession.get(this.GUEST_HASH));
        } catch (SQLException error) {
            // TODO: ERROR HERE
        }

        if (milestoneList != null) {

            HashMap<String, Object> viewBag = new HashMap<String, Object>();

            FlashMessage message = SessionFunctions.getFlashMessage(request);
            viewBag.put("hidenav", true); // HIDE THE NAVBAR
            viewBag.put("message", message);
            viewBag.put("total", milestoneList.size());
            viewBag.put("milestones", milestoneList);

            showView(response, "shared/guest-milestones-list.mustache", viewBag);

        }

    }


    protected boolean guestAuthOK(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map guestSession = getGuestSession(request);

        if (!guestSession.equals(null)) {

            String email = (String) guestSession.get(GUEST_KEY);
            String hash = (String) guestSession.get(GUEST_HASH);

            if (email == null || hash == null) {

                // SET A FLASH MESSAGE AND REDIRECT
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.INFO, "Restricted Access", "You can only access this area with a share link generated my a Milestone Tracker member."));
                response.sendRedirect(response.encodeRedirectURL("/shared")); //TODO: HARD CODED LOGIN
                return false;

            }


        }
        return true;

    }


}
