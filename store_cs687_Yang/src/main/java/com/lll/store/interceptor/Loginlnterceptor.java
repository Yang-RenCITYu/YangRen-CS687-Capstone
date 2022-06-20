package com.lll.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 定义一个拦截器 */
public class Loginlnterceptor implements HandlerInterceptor {

    /**
     *      * detect whether there is uid data in the global session object, if so, let it go, if not redirect to the login page
     *      * @param request request object
     *      * @param response response object
     *      * @param handler handler(url+Controller: mapping)
     *      * @return If the return value is true, the current request is released, if the return value is false, the current request is intercepted
     *      * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HttpServletResponse对象来获取session对象
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null){
            // 说明用户没有登录过系统，则重定向到login.html页面
            response.sendRedirect("/web/login.html");
            // 结束后续的调用
            return false;
        }
        // 请求放行
        return true;
    }
}
