package com.yurwar.trainingcourse.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Return home page to user
 *
 * @author Yurii Matora
 */
public class HomeCommand implements Command {

    HomeCommand() {
    }

    /**
     * @param request User http request to server
     * @return name of page or redirect
     */
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
