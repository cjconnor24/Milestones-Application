
package wpd2.teamR.servlet;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.MustacheRenderer;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * BASE SERVLET FROM WHICH TO EXTEND ALL OTHER BEHAVIOUR
 */
class BaseServlet extends HttpServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(BaseServlet.class);

    // SET THE CHARACTER SET AND FORMATTING
    static final String HTML_UTF_8 = "text/html; charset=UTF-8";
    static final String PLAIN_TEXT_UTF_8 = "text/plain; charset=UTF-8";
    static final String JSON = "application/javascript; charset=UTF-8";
    static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    // PROTECTED PAGEES LIST AND LOGIN PAGE ROUTE
    static final Set<String> PROTECTED_PAGES = new HashSet<>(Arrays.asList("/private"));
    static final String LOGIN_PAGE = "/login";

    // HOLDER FOR MUSTACHE RENDERRING
    private final MustacheRenderer mustache;

    /**
     * Constructor for base servlet - initialises the mustache renderer
     */
    BaseServlet() {
        mustache = new MustacheRenderer();
    }

    void issue(String mimeType, int returnCode, byte[] output, HttpServletResponse response) throws IOException {
        response.setContentType(mimeType);
        response.setStatus(returnCode);
        response.getOutputStream().write(output);
    }

    /**
     * Display a view to the user
     *
     * @param response     The http servlet response
     * @param templateName The name of the mustache template to render
     * @param model        The data model you wish to pass to the mustache renderer
     * @throws IOException
     */
    void showView(HttpServletResponse response, String templateName, Object model) throws IOException {
        String html = mustache.render(templateName, model);
        issue(HTML_UTF_8, HttpServletResponse.SC_OK, html.getBytes(CHARSET_UTF8), response);
    }

    // TODO: returnJSON method
    void returnJSON(HttpServletResponse response, Object model) throws IOException {
//        String html = mustache.render(templateName, model);
        // TODO: CONVERT THE MODEL TO JSON USING JACKSON
        issue(HTML_UTF_8, HttpServletResponse.SC_OK, model.toString().getBytes(CHARSET_UTF8), response);
    }

    /**
     * Check to see if the user is authenticated already
     *
     * @param request  The http servlet request
     * @param response The http servlet response
     * @return True or false depending on state
     * @throws IOException
     */
    protected boolean authOK(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String uri = request.getRequestURI();
//        String userName = UserFuncs.getCurrentUser(request);

        if (getCurrentUser(request).equals("")) {
            // SET A FLASH MESSAGE AND REDIRECT
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Login Required", "You cannot access this section without being logged in to the system."));
            response.sendRedirect(response.encodeRedirectURL("/login")); //TODO: HARD CODED LOGIN
            return false;
        }
        return true;

    }

    /**
     * Find the current user, if any
     *
     * @param request The HTTP request object, containing the session, if any
     * @return The current user, or the empty string if none (note NOT null)
     */
    protected String getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "";
        }
        String val = (String) session.getAttribute("email");
        return val == null ? "" : val;
    }

    /**
     * Write logged in used to session
     *
     * @param request  HTTP Request
     * @param userName Username to write to the session
     */
    protected void setCurrentUser(HttpServletRequest request, String userName) {
        HttpSession session = request.getSession(true);
        session.setAttribute("email", userName);
    }

    /**
     * Empty the user in the current session
     *
     * @param request HTTP Request
     */
    protected void clearCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("email"); // TODO: FIX THIS
    }

    protected void setCurrentProject(HttpServletRequest request, int projectId) {
        HttpSession session = request.getSession(true);
        session.setAttribute("projectId", projectId);
    }

    protected void clearCurrentProject(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.removeAttribute("projectId"); // TODO: FIX THIS
    }

    protected int getCurrentProject(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return -1;
        } else {

            // TRY AND FETCH THE PROJECT ID - IF NOT SET - RETURN FALSE
            try {

                int val = (int) session.getAttribute("projectId");
                return val;

            } catch (NullPointerException error) {

                return -1;

            }


        }

    }

    protected String getUrlParamter(String url) {
        String[] urlComponents = url.split("/");
        String parameter = urlComponents[urlComponents.length - 1];

        return parameter;
    }

}
