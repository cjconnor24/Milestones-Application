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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class MilestoneEditServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ProjectCreateServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";

    private MilestoneDAO milestones;

    public MilestoneEditServlet() {
        milestones = new MilestoneDAO();
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

        Milestone milestone = null;

        //GETTING PROJECT TO BE DELETED
        milestone = milestones.getMilestoneByIdandUser(id, getCurrentUser(request));

        // IF THE PROJECT EXISTS
        if (milestone != null) {

            // SHOW THE EDIT FORM
            HashMap<String, Object> viewBag = new HashMap<String, Object>();
            FlashMessage message = SessionFunctions.getFlashMessage(request);
            viewBag.put("mode","Edit"); //MAY NEED DELETE DEPENDING ON APPROCH!!
            viewBag.put("milestone", milestone);

            showView(response, "milestone/milestone-edit.mustache", viewBag);

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
        int milestoneID = Integer.parseInt(getUrlParamter(request.getRequestURI()));
        int checkParameter = Integer.parseInt(request.getParameter("id-check"));

        // CHECK THE PARAMETER MATCHES WHAT WAS SUBMITTED
        if(milestoneID == checkParameter){

            //=====================UGLY TIMESTAMP PARSEING - SOMEONE REFACTOR=============================
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDueDate = null;
            Date parsedDateCompleted = null;

            try {
                parsedDueDate = dateFormat.parse(request.getParameter("dueDate"));
                parsedDateCompleted = dateFormat.parse(request.getParameter("dateCompleted"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Timestamp updatedDueDate = new java.sql.Timestamp(parsedDueDate.getTime());
            Timestamp updatedDateCompleted= new java.sql.Timestamp(parsedDateCompleted.getTime());

            //==================================================

            Milestone updatedMilestone = new Milestone();
            updatedMilestone.setId(milestoneID);
            updatedMilestone.setName(request.getParameter("name"));
            updatedMilestone.setDescription(request.getParameter("description"));
            updatedMilestone.setDueDate(updatedDueDate);
            updatedMilestone.setDateCompleted(updatedDateCompleted);
            updatedMilestone.setDescription(request.getParameter("description"));

            // UPDATE
            if (milestones.updateMilestone(updatedMilestone)) {
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Milestone Update", "The Milestone was successfully updated!"));
            } else {
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Milestone Could Not Be Updated", "There was an issue updating!"));
            }


        } else {
            // REDIRECT
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Uh oh...", "The form request does match"));

        }



        response.sendRedirect("/milestones");
        return;

    }


    private void returnNotFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Project Could Not Be Found", "The project was not found, please refresh system!"));
        response.sendRedirect("/milestones");
        return;
    }

}



