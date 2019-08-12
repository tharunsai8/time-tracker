package com.yurwar.trainingcourse.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Logout user from system
 *
 * @author Yurii Matora
 */
public class LogoutCommand implements Command {

    LogoutCommand() {
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("success", true);
        return "/login.jsp";
    }
}
