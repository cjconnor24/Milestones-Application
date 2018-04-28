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
import wpd2.teamR.dao.MilestoneDAO;
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


public class LinkListServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LinkListServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private LinkDAO links;
    private ProjectDAO projects;


    public LinkListServlet() {
        links = new LinkDAO();
        projects = new ProjectDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        List<Link> llist = new ArrayList<Link>();
        try {

            llist = links.findAllByUserEmail(getCurrentUser(request));
            llist.forEach((v) -> {
                try {
                    v.setProject(projects.getProjectById(v.getProjectID()));
                } catch (SQLException error) {
                    // TODO: SOMETHING WENT WRONG - GENERATE ERROR
                }
            });

        } catch (SQLException error) {
            // TODO: SOMETHING WENT WRONG - GENERATE ERROR
        }

        HashMap<String, Object> viewBag = new HashMap<String, Object>();

        FlashMessage message = SessionFunctions.getFlashMessage(request);
//        viewBag.put("username",userName);
        viewBag.put("message", message);
        viewBag.put("total", llist.size());
        viewBag.put("links", llist);

        showView(response, "link/link-list.mustache", viewBag);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Project p = new Project();
//        p.setName(request.getParameter("name"));
//        p.setDescription(request.getParameter("description"));
//
//        if (projects.createProject(p, getCurrentUser(request))) {
//
//            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Project Added", "Your project was added"));
//            response.sendRedirect("/private");
//            return;
//
//        }

    }


}
