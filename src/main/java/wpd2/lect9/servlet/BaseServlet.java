
package wpd2.lect9.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.lect9.util.MustacheRenderer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param response The http servlet response
     * @param templateName The name of the mustache template to render
     * @param model The data model you wish to pass to the mustache renderer
     * @throws IOException
     */
    void showView(HttpServletResponse response, String templateName, Object model) throws IOException {
        String html = mustache.render(templateName, model);
        issue(HTML_UTF_8, HttpServletResponse.SC_OK, html.getBytes(CHARSET_UTF8), response);
    }

    /**
     * Check to see if the user is authenticated already
     * @param request The http servlet request
     * @param response The http servlet response
     * @return True or false depending on state
     * @throws IOException
     */
    boolean authOK(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String userName = UserFuncs.getCurrentUser(request);
        if (PROTECTED_PAGES.contains(uri) && "".equals(userName)) {
            UserFuncs.setLoginRedirect(request);
            response.sendRedirect(response.encodeRedirectURL(LOGIN_PAGE));
            return false;
        }
        return true;
    }

}