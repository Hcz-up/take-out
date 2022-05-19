package com.hcz.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.hcz.reggie.common.BaseContext;
import com.hcz.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title: LoginCheckFilter
 * @Author Tan
 * @Date: 2022/4/24 11:13
 * @Version 1.0
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1、获取本次请求的uri
        String requestURI = request.getRequestURI();

        // 2、判断本次请求是否需要处理
        // 定义不需要拦截的uri
        String[]  uris = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg", //移动端发送短信
                "/user/login"  // 移动段登录
        };
        boolean check = check(uris, requestURI);

        // 3、如果不需要处理，则直接放行
        if(check){
            filterChain.doFilter(request, response);
            return;
        }

        // 4-1、判断登录状态，如果已登录，则放行
        if(request.getSession().getAttribute("employee") != null){
            Long employeeId = (Long)request.getSession().getAttribute("employee");
            BaseContext.setId(employeeId);

            filterChain.doFilter(request, response);
//            log.info("" + Thread.currentThread().getId());

            return;
        }

        // 4-2、判断登录状态，如果已登录，则放行
        if(request.getSession().getAttribute("user") != null){
            Long userId = (Long)request.getSession().getAttribute("user");
            BaseContext.setId(userId);

            filterChain.doFilter(request, response);
//            log.info("" + Thread.currentThread().getId());

            return;
        }

        // 5、如果未登录则通过输出流的方式告诉前端未登录
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 请求路径的匹配
     * @param uris
     * @param requestUrl
     * @return
     */
    private boolean check(String[] uris, String requestUrl){
        for (String uri : uris) {
            boolean match = PATH_MATCHER.match(uri, requestUrl);
            if(match)
                return true;
        }

        return false;
    }
}
