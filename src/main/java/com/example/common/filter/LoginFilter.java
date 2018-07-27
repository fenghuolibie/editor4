package com.example.common.filter;

import com.example.common.util.CommonResult;
import net.sf.json.JSONObject;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(value = 2)
//@WebFilter(filterName="/CodeFilter",urlPatterns={"/api/v1/*","/recode/*"}
//        ,initParams={@WebInitParam(name ="EXCLUDED_PAGES" , value = "api/loginCheck")})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.getSession().getAttribute("userbean") == null){
            response.setContentType("application/json;charset=UTF-8");
            CommonResult result = new CommonResult();
            result.setResponseCode(000);
            result.setResponseMessage(new String("你还未进行登录"));
            JSONObject jsonObject = JSONObject.fromObject(result.toString());
            response.getWriter().write(String.valueOf(jsonObject));
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}