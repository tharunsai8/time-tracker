package com.yurwar.trainingcourse.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter that react on changing of parameter lang and set it to the session scope
 */
public class LocalizationFilter implements Filter {
    private static final Logger log = LogManager.getLogger();

    /**
     * Set lang parameter in session scope
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (request.getParameter("lang") != null) {

            log.debug("Changing language on " + request.getParameter("lang"));

            request.getSession().setAttribute("lang", request.getParameter("lang"));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
