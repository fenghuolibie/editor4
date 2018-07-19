package com.example.configuration.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebServlet;


/**
 * StatViewServlet用于展示Druid的统计信息。
 * 提供监控信息展示的html页面
 * 提供监控信息的JSON API
 *
 * 内置监控页面的首页是/druid/index.html
 *
 */

/**
 * @Webservlet
 * 有两个属性可以用来表示Servlet的访问路径，分别是value和urlPatterns。value和urlPatterns都是数组形式，
 * 表示我们可以把一个Servlet映射到多个访问路径，但是value和urlPatterns不能同时使用。
 *
 */
@WebServlet(urlPatterns="/druid/*")
//        initParams={
//                //@WebInitParam(name="allow",value="127.0.0.1,192.168.163.1"),// IP白名单(没有配置或者为空，则允许所有访问)
//                //@WebInitParam(name="deny",value="192.168.1.73"),// IP黑名单 (与白名单存在共同时，deny优先于allow)
////                @WebInitParam(name="loginUsername",value="tommy"),// 用户名
////                @WebInitParam(name="loginPassword",value="123456"),// 密码
//                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
//        })
public class DruidStatViewServlet extends StatViewServlet {
    private static final long serialVersionUID = -2688872071445249539L;
}
