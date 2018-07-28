package com.hk.dream.onlyDream.configuration;

/**
 * 坤
 * 2018/7/25 19:23
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@ControllerAdvice
public class MyCustomExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(MyCustomExceptionHandler.class.getName());
    /**
     * 自定义异常数据
     * 缺点，没有适配页面和Ajax请求，返回的数据都是json数据
     * @param req
     * @return
     */
    /*@ResponseBody
    @ExceptionHandler(Exception.class)
    public Map<String, Object> exceptionHandler(HttpServletRequest req){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", 500);
        map.put("errorMsg", "错误信息");
        map.put("errorSystem", "errorSystem");
        return map;
    }*/

    /**
     * 自定义异常数据
     * 适配页面和Ajax请求
     * 注解ExceptionHandler(Exception.class)的Exception.class可以替换成自己定义的错误异常类
     * @param req
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(HttpServletRequest req, Exception e){
        logger.error(e.getMessage(),e);
        req.setAttribute("msg", "服务器出错："+e.toString());
        //传入自己的错误代码，必须的，否则不会进入自定义错误页面，见：org.springframework.boot.autoconfigure.web.AbstractErrorController
        req.setAttribute("javax.servlet.html.status_code", 200);

        //转发到springBoot错误处理请求，能适配网页和Ajax的错误处理
        //请求/error后，会进入BasicErrorController(@RequestMapping("${server.html.path:${html.path:/html}}"))
        //页面的数据显示处理是使用：errorAttributes.getErrorAttributes获取显示的，是AbstractErrorController的方法
        //当需要把自己定义的Map错误信息传递到错误提示页面时，
        //可以编写一个自定义错误属性类处理：CustomErrorAttribute，继承DefaultErrorAttributes类，
        //重写getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace)方法
        return "forward:/error";
    }


    /******************************************************************************************************/
    @Controller
    public class MyErrorController extends BasicErrorController {

        public MyErrorController(ServerProperties serverProperties) {
            super(new DefaultErrorAttributes(), serverProperties.getError());
        }

        /**
         * 覆盖默认的Json响应
         */
        @Override
        public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
            Map<String, Object> map = getErrorAttributes(request,
                    isIncludeStackTrace(request, MediaType.ALL));
            HttpStatus status = getStatus(request);
//        System.out.println(new ToString(body).toString());
            //输出自定义的Json格式
//        Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 1);
            map.put("msg", request.getAttribute("msg"));
            return new ResponseEntity<Map<String, Object>>(map, status);
        }

        /**
         * 覆盖默认的HTML响应
         */
        @Override
        public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
//        System.out.println("HTML"+request.getAttribute("html"));
            //请求的状态
//        HttpStatus status = getStatus(request);
//        response.setStatus(getStatus(request).value());
//
//        Map<String, Object> model = getErrorAttributes(request,
//                isIncludeStackTrace(request, MediaType.TEXT_HTML));
//        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
            //指定自定义的视图
//        return(modelAndView == null ? new ModelAndView("3", model) : modelAndView);
            ModelAndView mv = new ModelAndView();
            mv.setViewName("html/500");
            if(response.getStatus() == 404)mv.setViewName("html/404");
            mv.addObject("msg",request.getAttribute("msg"));
            return mv;
        }
    }
}