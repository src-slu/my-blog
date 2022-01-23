package com.ls.springcloud.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @ClassName BaseController
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 14:13
 */
@Slf4j
@Configuration
public class BaseController implements Serializable {


    private static final long serialVersionUID = 6970710228211778506L;

    public Item getItem(){
        return new Item(this.getRequest());
    }

    private HttpServletRequest getRequest(){
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

}
