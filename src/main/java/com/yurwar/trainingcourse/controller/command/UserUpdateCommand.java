package com.yurwar.trainingcourse.controller.command;

import javax.servlet.http.HttpServletRequest;

public class UserUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        return null;
    }
}
