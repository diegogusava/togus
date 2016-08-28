package br.com.diegogusava.togus.infrastructure;

import com.google.common.net.HttpHeaders;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter", urlPatterns = {"/rest/*"})
public class CorsFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, DELETE, GET, HEAD, OPTIONS");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, X-Total-Count");
        res.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LINK);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
