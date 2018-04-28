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
import wpd2.teamR.dao.LinkDAO;
import wpd2.teamR.dao.ProjectDAO;
import wpd2.teamR.models.Link;
import wpd2.teamR.models.Project;
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


public class LinkCreateServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LinkCreateServlet.class);

    // CONNECTION TO DAO
    private ProjectDAO projects;
    private LinkDAO links;

    /**
     * Initialise servlet and get a copy of all projects
     */
    public LinkCreateServlet() {
        projects = new ProjectDAO();
        links = new LinkDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        List<Project> projectList = new ArrayList<>();
        try {
            projectList = projects.getProjectsbyUser(getCurrentUser(request));
        } catch (SQLException error) {
            //TODO: SOMETHING WENT WRONG
        }

        // SETUP VIEWBAG TO SEND TO VIEW
        HashMap<String, Object> viewBag = new HashMap<String, Object>();
        FlashMessage message = SessionFunctions.getFlashMessage(request);
        viewBag.put("projects", projectList);
        viewBag.put("mode", "Create");
        viewBag.put("message", message);

        // RENDER CREATE FORM
        showView(response, "link/link-create.mustache", viewBag);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {

        // BUILD THE NEW PROJECT - TODO: SERVER SIDE VALIDATION - jQuery / html catching for now.

        String email = request.getParameter("email");
        int projectID = Integer.parseInt(request.getParameter("project"));

        Link l = new Link(email, projectID);
        l.setEmail(request.getParameter("email"));
        l.setProjectID(projectID);

        // IF IT WAS SUCCESSFULLY CREATED
        if (links.save(l, projectID)) {

            // SAVE A SUCCESSFUL FLASH MESSAGE AND RETURN TO PROJECT VIEW
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Link Created", "Your share link was created :)"));
            response.sendRedirect("/links");
            return;

        } else {

            // SOMETHING WENT WRONG - SEND THEM BACK TO FORM WITH ERROR
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Uh oh...", "Sorry, something went wrong"));
            response.sendRedirect("/links/create");
        }

    }


}
