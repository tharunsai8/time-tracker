package com.yurwar.trainingcourse.controller.command;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements Command {

    HomeCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
