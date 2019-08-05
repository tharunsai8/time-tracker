package com.yurwar.trainingcourse.controller.filter;

import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AuthFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    private final List<String> adminPaths = Arrays.asList(
            "/index",
            "/logout",
            "/profile",
            "/users",
            "/users/delete",
            "/users/update",
            "/activities",
            "/activities/add",
            "/activities/request",
            "/activities/delete",
            "/activities/mark-time");
    private final List<String> userPaths = Arrays.asList(
            "/index",
            "/logout",
            "/profile",
            "/activities",
            "/activities/mark-time");
    private final List<String> defaultPaths = Arrays.asList(
            "/index",
            "/login",
            "/registration");
    private Map<Authority, List<String>> authPaths = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authPaths.put(Authority.USER, userPaths);
        authPaths.put(Authority.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI().replaceAll(".*/app", "");

        User user = (User) session.getAttribute("authUser");

        if (Objects.isNull(user)) {
            if (defaultPaths.contains(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() +
                        "/login");
                return;
            }
        }

        List<String> paths = user.getAuthorities()
                .stream()
                .flatMap(authority -> authPaths.get(authority).stream())
                .distinct()
                .collect(Collectors.toList());

        if (paths.contains(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/error/403.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
