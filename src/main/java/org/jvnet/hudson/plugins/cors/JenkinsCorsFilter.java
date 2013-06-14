package org.jvnet.hudson.plugins.cors;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Add CORS header to resonse.
 * 
 * @author Jochen Hinrichsen
 */
public class JenkinsCorsFilter implements Filter {


    private static final String CORS_HANDLE_OPTIONS_METHOD = System.getProperty("cors.options", "true");
    private static final String CORS_METHODS = System.getProperty("cors.methods", "GET, POST, PUT, DELETE");
    private static final String[] CORS_WHITELIST = {"https://dashboard.cloudbees.com", "http://localhost.cloudbees.com:8080", "https://dashboard.beescloud.com"};

    /** {@inheritDoc} */
    public void init(FilterConfig filterConfig) throws ServletException {}

    /** {@inheritDoc} */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (response instanceof HttpServletResponse) {

            final HttpServletResponse resp = (HttpServletResponse) response;
            if(request instanceof HttpServletRequest && Boolean.valueOf(CORS_HANDLE_OPTIONS_METHOD)) {
                HttpServletRequest req = (HttpServletRequest)request;
                maybeAddAllowHeaders(req, resp);
                if("OPTIONS".equals(req.getMethod())) {
                    resp.setStatus(200);
                   return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private void maybeAddAllowHeaders(HttpServletRequest req, HttpServletResponse resp) {
        String origin = req.getHeader("Origin");
        if (origin != null && inWhitelist(origin.trim())) {
            resp.addHeader("Access-Control-Allow-Methods", CORS_METHODS);
            resp.addHeader("Access-Control-Allow-Credentials", "true");
            resp.addHeader("Access-Control-Allow-Origin", origin);
        }
    }

    private boolean inWhitelist(String allow) {
        for (int i = 0; i < CORS_WHITELIST.length - 1 ; i++) {
            if (CORS_WHITELIST[i].equals(allow)) return true;
        }
        return false;
    }

    /** {@inheritDoc} */
    public void destroy() {}
}