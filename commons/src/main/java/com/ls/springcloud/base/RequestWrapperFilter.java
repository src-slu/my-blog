package com.ls.springcloud.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName RequestWrapperFilter
 * @Description 请求过滤器
 * @Author lushuai
 * @Date 2019/11/14 15:11
 */
@WebFilter(filterName = "requestWrapperFilter", urlPatterns = { "/*" })
@Configuration
public class RequestWrapperFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            /**
             * 目前不需要拦截器，后续在使用。
             * 当前直接返回CommandData对象，进行request请求解析
             */
            requestWrapper = new RequestWrapper(httpRequest);
//            new CommandData(httpRequest);
        }else if(request instanceof MultipartHttpServletRequest){
            /**
             * 上传文件不做封装
             */
            MultipartHttpServletRequest httpRequest = (MultipartHttpServletRequest) request;
            requestWrapper = new RequestWrapper(httpRequest);
        }

        if(requestWrapper == null){
            filterChain.doFilter(request, response);
        }else{
            filterChain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
