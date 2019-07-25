package com.yurwar.trainingcourse.controller.filter;

import com.yurwar.trainingcourse.model.entity.Role;
import com.yurwar.trainingcourse.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class AuthFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    private final List<String> adminPaths = Arrays.asList("/index", "/logout", "/users", "/users/delete");
    private final List<String> userPaths = Arrays.asList("/index", "/logout");
    private final List<String> defaultPaths = Arrays.asList("/index", "/login", "/registration");
    private Map<Role, List<String>> allowedPathPatterns = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPathPatterns.put(Role.USER, userPaths);
        allowedPathPatterns.put(Role.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI().replaceAll(".*/app", "");

        User user = (User) session.getAttribute("user");

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

        List<String> paths = allowedPathPatterns.getOrDefault(user.getRole(), defaultPaths);

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
