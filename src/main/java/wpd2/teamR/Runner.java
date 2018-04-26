package wpd2.teamR;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.servlet.*;

public class Runner {
    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    private static final int PORT = 9001;


    private Runner() {
    }

    /**
     * Start server
     *
     * @throws Exception
     */
    private void start() throws Exception {

        // SETUP THE SERVER AND PASS THE PORT NUMBER
        Server server = new Server(PORT);

        // SETUP SESSIONS, BASE PATH AND RESOURCE PATH
        ServletContextHandler handler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.setInitParameter("org.eclipse.jetty.servlet.Default." + "resourceBase", "src/main/resources/webapp");

        // SETUP DEFAULT SERVLET AND ADD DEFAULT SERVLET HOLDER
        DefaultServlet ds = new DefaultServlet();
        handler.addServlet(new ServletHolder(ds), "/");


        // OUR ROUTES
        handler.addServlet(new ServletHolder(new ProjectListServlet()), "/projects");
        handler.addServlet(new ServletHolder(new ProjectCreateServlet()), "/projects/create");
        handler.addServlet(new ServletHolder(new ProjectDeleteServlet()), "/projects/delete/*");
        handler.addServlet(new ServletHolder(new ProjectEditServlet()), "/projects/edit/*");

        handler.addServlet(new ServletHolder(new PrivatePageServlet()), "/private");
        handler.addServlet(new ServletHolder(new LoginServlet()), "/login");
        handler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");


        // START THE SERVER
        server.start();
        LOG.info("Server started, will run until terminated");
        server.join();

    }

    public static void main(String[] args) {
        try {

            // START THE SERVER ABOVE
            LOG.info("starting...");
            new Runner().start();

        } catch (Exception e) {

            // SOMETHING WENT WRONG
            LOG.error("Unexpected error running: " + e.getMessage());

        }
    }
}
