// Copyright (c) 2018 Cilogi. All Rights Reserved.
//
// File:        LoginServlet.java
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package wpd2.teamR.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wpd2.teamR.dao.ProjectDAO;
import wpd2.teamR.models.Project;
import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class ProjectEditServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ProjectCreateServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private ProjectDAO projects;

    public ProjectEditServlet() {
        projects = new ProjectDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        //GETTING ID FROM URL
        int id = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        Project project = null;

        //GETTING PROJECT TO BE DELETED
        try {
            project = projects.getProjectByIdAndUser(id, getCurrentUser(request));
        } catch (SQLException e) {
            returnNotFound(request, response);
        }

        // IF THE PROJECT EXISTS
        if (project != null) {

            // SHOW THE EDIT FORM
            HashMap<String, Object> viewBag = new HashMap<String, Object>();
            FlashMessage message = SessionFunctions.getFlashMessage(request);
            viewBag.put("mode", "Edit");
            viewBag.put("project", project);

            showView(response, "project/project-edit.mustache", viewBag);

        } else {
            // OTHERWISE BOUNCE WITH ERROR
            returnNotFound(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        //GETTING ID FROM URL
        int projectID = Integer.parseInt(getUrlParamter(request.getRequestURI()));
        int checkParameter = Integer.parseInt(request.getParameter("id-check"));

        // CHECK THE PARAMETER MATCHES WHAT WAS SUBMITTED
        if (projectID == checkParameter) {

            // BUILD THE OBJECT AND SAVE IT
            Project updatedProject = new Project();
            updatedProject.setId(projectID);
            updatedProject.setName(request.getParameter("name"));
            updatedProject.setDescription(request.getParameter("description"));

            // UPDATE
            if (projects.projectUpdate(updatedProject)) {
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Project Update", "The project was successfully updated!"));
            } else {
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Project Could Not Be Updated", "There was an issue updating!"));
            }


        } else {
            // REDIRECT
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Uh oh...", "The form request does match"));

        }


        response.sendRedirect("/projects");
        return;

    }


    private void returnNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Project Could Not Be Found", "The project was not found, please refresh system!"));
        response.sendRedirect("/projects");
        return;
    }

}



