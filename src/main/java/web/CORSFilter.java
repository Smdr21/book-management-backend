package web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CORSFilter implements Filter {

    private ServletContext context;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        System.out.println("CORSFILTER HTTP Request: "+ request.getMethod());

        response.addHeader("Access-Control-Allow-Origin","http://localhost:3000");
        response.addHeader("Access-Control-Allow-Methods","GET, DELETE, PUT, POST");
        response.addHeader("Access-Control-Allow-Methods","GET, DELETE, PUT, POST");
        filterChain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context=filterConfig.getServletContext();
    }

    @Override
    public void destroy() {

    }
}
