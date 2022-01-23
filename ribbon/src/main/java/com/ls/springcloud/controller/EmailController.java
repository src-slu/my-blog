package com.ls.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Emailcontroller
 * @Description
 * @Author lushuai
 * @Date 2019/11/11 10:39
 */
@RestController
@RequestMapping("email")
public class EmailController {


    @RequestMapping("/send")
    public void sendEmail(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> strings = parameterMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while(iterator.hasNext()){
            String nextKey = iterator.next();
            System.out.println(Arrays.toString(parameterMap.get(nextKey)));
        }
    }
}
