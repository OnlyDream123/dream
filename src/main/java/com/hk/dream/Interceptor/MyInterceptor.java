package com.hk.dream.Interceptor;

import com.hk.dream.util.DreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 坤
 * 2018/7/20 21:10
 */

public class MyInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
    public final AtomicLong _count = new AtomicLong();// 计数器
    //在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long begin_nao_time = System.nanoTime();
        String realIp = DreamUtil.getIpAddress(httpServletRequest);
        httpServletRequest.setAttribute("p_real_ip", realIp);
        httpServletRequest.setAttribute("begin_nao_time", begin_nao_time);
        return true;    //如果false，停止流程，api被拦截
    }

    //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("完成业务处理");
    }

    //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long begin_nao_time = (Long) httpServletRequest.getAttribute("begin_nao_time");
        String real_ip = (String) httpServletRequest.getAttribute("p_real_ip");
        long interval = System.nanoTime() - begin_nao_time;
        String uri = httpServletRequest.getRequestURI();
        logger.info("响应结束 路径："+uri+"\t\tIP："+real_ip+"\t\t用时："+(new BigDecimal(interval).divide(new BigDecimal(1000000000),2,3)+"秒"));
    }

}
@Configuration
class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径 如果排除某个类型比如css和js文件 excludePathPatterns("/**/*.css","/**/*.js")
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/public/**",
                "/**/*.css",
                "/**/*.js",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.woff"
                );
    }
}
