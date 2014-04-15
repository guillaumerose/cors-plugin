package org.jvnet.hudson.plugins.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Add CORS header to resonse.
 * 
 * @author Jochen Hinrichsen
 */
public class JenkinsCorsFilter implements Filter
{

    private static final String CORS_HANDLE_OPTIONS_METHOD = System.getProperty("cors.options", "true");
    private static final String CORS_METHODS = System.getProperty("cors.methods", "GET, POST, PUT, DELETE");

    public void init(FilterConfig filterConfig) throws ServletException
    {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if (response instanceof HttpServletResponse)
        {

            final HttpServletResponse resp = (HttpServletResponse) response;
            if (request instanceof HttpServletRequest && Boolean.valueOf(CORS_HANDLE_OPTIONS_METHOD))
            {
                HttpServletRequest req = (HttpServletRequest) request;
                addHeaders(req, resp);
                if ("OPTIONS".equals(req.getMethod()))
                {
                    resp.setStatus(200);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private static void addHeaders(HttpServletRequest req, HttpServletResponse resp)
    {
        String origin = req.getHeader("Origin");
        if (origin != null)
        {
            resp.addHeader("Access-Control-Allow-Methods", CORS_METHODS);
            resp.addHeader("Access-Control-Allow-Credentials", "true");
            resp.addHeader("Access-Control-Allow-Origin", origin);
        }
    }

    public void destroy()
    {}
}