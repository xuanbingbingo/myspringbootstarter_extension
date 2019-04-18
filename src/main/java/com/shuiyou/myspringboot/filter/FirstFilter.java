package com.shuiyou.myspringboot.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "FirstFilter",urlPatterns = "/out")
public class FirstFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("进入Filter");
        chain.doFilter(req, resp);
        System.out.println("离开Filter");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
