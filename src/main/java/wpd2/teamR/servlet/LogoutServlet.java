package wpd2.teamR.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LogoutServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LogoutServlet.class);

    public LogoutServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Successfully Logged Out", "You have been successfully logged out of the system"));

        // CLEAR ENTIRE SESSION
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

//        clearCurrentUser(request);
        response.sendRedirect(response.encodeRedirectURL("/login"));
    }
}
