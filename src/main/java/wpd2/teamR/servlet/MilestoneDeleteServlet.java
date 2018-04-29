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

import wpd2.teamR.dao.MilestoneDAO;
import wpd2.teamR.dao.ProjectDAO;

import wpd2.teamR.models.Milestone;
import wpd2.teamR.models.Project;

import wpd2.teamR.util.FlashMessage;
import wpd2.teamR.util.SessionFunctions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class MilestoneDeleteServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ProjectCreateServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private MilestoneDAO milestones;

    public MilestoneDeleteServlet() {
        milestones = new MilestoneDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //IS THIS NEEDED HERE?????
        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }
        String email = getCurrentUser(request);

        //GETTING ID FROM URL
        int id = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        Milestone milestoneToDelete = null;

        //GETTING PROJECT TO BE DELETED
        milestoneToDelete = milestones.getMilestoneByIdandUser(id,email);

        if (milestoneToDelete != null) {
            HashMap<String, Object> viewBag = new HashMap<String, Object>();

            FlashMessage message = SessionFunctions.getFlashMessage(request);
            viewBag.put("milestone", milestoneToDelete);

            showView(response, "milestone/milestone-delete.mustache", viewBag);
        } else {
            returnNotFound(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //IS THIS NEEDED HERE?????
        // CHECK IF USER IS LOGGED IN - IF NOT BOUNCE TO LOGIN
        if (!authOK(request, response)) {
            return;
        }

        //GETTING ID FROM URL
        int parameter = Integer.parseInt(getUrlParamter(request.getRequestURI()));

        //CHECKS IF DELETED OR NOT AND RETURNS CORRECT RESPONSE
        if (milestones.deleteMilestoneById(parameter)) {
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Milestone Deleted", "The Milestone was deleted"));
        } else {
           SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Milestone Could Not Be Deleted", "The Milestone was not deleted!"));
        }

        response.sendRedirect("/milestones");
        return;

    }


    private void returnNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "milestones Could Not Be Found", "The milestones was not found, please refresh system!"));
        response.sendRedirect("/milestones");
        return;
    }

}



