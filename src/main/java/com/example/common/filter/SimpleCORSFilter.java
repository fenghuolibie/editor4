


package com.example.common.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 用于处理跨域
 *
 * @author JSX ASKYOFCODE@foxmail.com
 * @version 1.0
 * @Title SimpleCORSFilter
 * @Package com.powater.share.common.filter
 * @Copyright 江西鄱信息科技有限公司
 * @date 2018/6/19/019
 * @see 1.8.0_151
 * @since
 */
@Order(value = 1)
@WebFilter(urlPatterns = "/*")
public class SimpleCORSFilter implements Filter {

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,token,content-type,X-Custom-Header,Authorization");
        //response.setHeader("Access-Control-Expose-Headers","Cache-Control,Content-Type,Expires,Pragma,Content-Language,Last-Modified,token,Authorization");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
