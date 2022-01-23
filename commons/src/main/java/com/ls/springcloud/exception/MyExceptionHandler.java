package com.ls.springcloud.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName MyExceptionHandler
 * @Description 异常捕获
 * @Author lushuai
 * @Date 2019/11/18 10:07
 */
@ControllerAdvice(basePackages = "com.ls.springcloud")
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView errorHandler(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMsg", "那啥\n出了点小问题哈。。。");
        mav.setViewName("error");
        return mav;
    }
}
