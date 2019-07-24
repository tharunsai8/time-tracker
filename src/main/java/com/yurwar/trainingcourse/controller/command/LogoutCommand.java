package com.yurwar.trainingcourse.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final Logger log = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        session.invalidate();
//        session.removeAttribute("user");
//        session.removeAttribute("username");
//        session.removeAttribute("role");
        request.setAttribute("success", true);
        log.info("User " + username + " logout successfully");
        return "redirect:login";
    }
}
