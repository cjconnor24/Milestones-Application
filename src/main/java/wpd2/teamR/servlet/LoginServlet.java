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
import wpd2.teamR.dao.UserDAO;
import wpd2.teamR.models.User;
import wpd2.teamR.util.FlashMessage;

import wpd2.teamR.util.SessionFunctions;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class LoginServlet extends BaseServlet {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    private final String LOGIN_TEMPLATE = "login.mustache";
    private final String SUCCESS_REDIRECT = "/projects";

    private UserDAO users;

    public LoginServlet() {
        users = new UserDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = getCurrentUser(request);


        HashMap<String, Object> viewBag = new HashMap<String, Object>();

        FlashMessage message = SessionFunctions.getFlashMessage(request);
        viewBag.put("hidenav",true); // HIDE THE NAVBAR
        viewBag.put("username", userName);
        viewBag.put("message", message);

        showView(response, LOGIN_TEMPLATE, viewBag);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // CHECK WHETHER USER WISHES TO LOGIN OR REGISTER
        if (request.getParameter("buttons").equals("register")) {

            // CARRY OUT REGISTRATION LOGIC
            this.register(request, response);

        } else {

            // CARRY OUT LOGIN LOGIC
            this.login(request, response);

        }

    }

    /**
     * Login user, based on data passed from form.
     *
     * @param request  HTTP Servlet Request
     * @param response HTTP Servlet Response
     * @throws IOException
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // GET THE PARAMS FROM THE FORM
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

            // CHECK USER EXISTS AND PASSWORD MATCHES
            String result = users.checkIsValidUser(email, password);
            if (!result.isEmpty()) {

                // TRUE, SO SET SESSION AND REDIRECT TO PAGE
                setCurrentUser(request, result);
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Successfully logged in", "Welcome back :)"));
                response.sendRedirect(SUCCESS_REDIRECT);

            } else {

                // LOGIN WASNT SUCCESSFUL
                SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "There was a problem", "Please check your login details"));
                response.sendRedirect("/login");

            }

        } catch (SQLException error) {

            LOG.debug(error.toString());

        }

        LOG.debug("This should have saved the user in DB");

    }

    /**
     * Register the user in the DB
     *
     * @param request  Http Request
     * @param response Http Response
     * @throws IOException
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // GET THE PARAMS FROM THE FORM
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // CREATE NEW USER BASED ON FORM DATA
        User newUser = new User(fname, lname, email, password);

        // WRITE THE NEW USER TO THE DATABASE
        if (users.registerUser(newUser)) {

            // TODO: MAYBE EMAIL
            // SET A SESSION, WRITE A SUCCESS MESSAGE, AND REDIRECT
            setCurrentUser(request, email);
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.SUCCESS, "Successfully Registered", "You have been successfully registered in the sytem. Welcome."));
            response.sendRedirect(SUCCESS_REDIRECT);

            LOG.debug("This should have saved the user in DB");

        } else {

            // WASNT A SUCCESS
            SessionFunctions.setFlashMessage(request, new FlashMessage(FlashMessage.FlashType.ERROR, "Uh oh", "Something went wrong, please try again."));
            response.sendRedirect("/login");

            LOG.debug("This did not save in the DB");

        }


    }

}
