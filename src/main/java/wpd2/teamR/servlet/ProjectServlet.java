package wpd2.teamR.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.dao.ProjectDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectServlet extends HttpServlet{

    static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    private final String PROJECT_TEMPLATE = "project-list.mustache";

    ProjectDAO projects;

    public ProjectServlet(){ projects = new ProjectDAO();}

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String hello = "Hello";

        System.out.print(hello);

        }



}
