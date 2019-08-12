package com.yurwar.trainingcourse.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Set encoding of server responses
 */
public class EncodingFilter implements Filter {
    /**
     * Set response content type and character encoding of response and request
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
