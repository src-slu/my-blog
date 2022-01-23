package com.ls.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName MyZuulFilter
 * @Description
 * @Author lushuai
 * @Date 2020/1/3 14:02
 */
@Component
public class MyZuulFilter extends ZuulFilter {

    /**
     * 过滤类型
     * pre：路由之前
     * routing：路由时
     * post：路由后
     * error：发生错误时调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤优先级
     * 数字越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否使用过滤
     * true：启用过滤（调用run()方法）
     * false：禁用过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();

        /**
         * 不拦截登陆和注册
         */
        if(requestURI.contains("login") || requestURI.contains("registry")){
            return false;
        }

        return true;
    }

    /**
     * 过滤器具体实现
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() {

        /**
         * 通过RequestContext获取当前上下文context
         * 通过获取的上下文context来获取request请求
         */
        RequestContext context = RequestContext.getCurrentContext();
        try {
            doProcess(context);
            // 校验需要认证的信息
            // ...
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拦截非法请求
     * @param context
     */
    private void doProcess(RequestContext context) {
        try {
            HttpServletRequest request = context.getRequest();
            String uri = request.getRequestURI();
            if(!uri.contains("login")){
                String tokenId = request.getHeader("tokenId");
                /**
                 * 拦截请求，判断请求是否携带token，不携带拒绝访问
                 */
                if(tokenId == null || "".equals(tokenId)){
                    context.setResponseStatusCode(401);
                    context.setResponseBody("非法请求, 拒绝访问!!!");
                }
                /**
                 * 将获取的tokenId放入请求参数中
                 */
                context.put("tokenId", tokenId);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
