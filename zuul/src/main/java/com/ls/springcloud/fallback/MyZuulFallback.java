package com.ls.springcloud.fallback;

import com.ls.springcloud.base.Response;
import com.ls.springcloud.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName MyZuulFallback
 * @Description 自定义Zuul服务降级
 * @Author lushuai
 * @Date 2020/1/6 16:27
 */
@Component
@Slf4j
public class MyZuulFallback implements FallbackProvider {

    /**
     * 获取路由
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 服务降级处理
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if(cause != null && cause.getCause() != null){
            log.warn("熔断器捕获异常: route → {}, cause → {}", route, cause.getCause().getMessage());
        }
        return response(HttpStatus.BAD_REQUEST);
    }

    /**
     * 返回错误信息
     * @param status
     * @return
     */
    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            /**
             * 错误码
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            /**
             * 错误代码
             * @return
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            /**
             * 错误原因
             * @return
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            /**
             * 关闭服务降级
             */
            @Override
            public void close() {}

            /**
             * 设置返回的错误信息
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                int code = status.value();
                String msg = status.getReasonPhrase();
//                if(cause instanceof TimeoutException) {
//                    code = 504;
//                    msg = "gateway timeout";
//                }else{
//                    code = 200;
//                    msg = "Unprocessed completion";
//                }
                String response = JsonUtils.objToString(Response.response(code, msg));
                return new ByteArrayInputStream(response.getBytes());
            }

            /**
             * 设置头部信息
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
//                headers.set
                return headers;
            }
        };
    }
}
