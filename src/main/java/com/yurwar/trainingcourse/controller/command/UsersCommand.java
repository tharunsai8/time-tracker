package com.yurwar.trainingcourse.controller.command;

import com.yurwar.trainingcourse.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UsersCommand implements Command {
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        if (request.getParameter("size") != null) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        long numberOfRecords = userService.getNumberOfRecords();
        long totalPages = (long) Math.ceil((double) numberOfRecords / size);

        request.setAttribute("users", userService.getAllUsersPageable(page, size));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", size);
        request.setAttribute("totalPages", totalPages);
        return "/users.jsp";
    }
}
