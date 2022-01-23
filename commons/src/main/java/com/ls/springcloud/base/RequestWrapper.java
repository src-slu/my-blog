package com.ls.springcloud.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @ClassName RequestWrapper
 * @Description
 * @Author lushuai
 * @Date 2019/11/14 14:57
 */
public class RequestWrapper extends HttpServletRequestWrapper{

    //    private final byte[] body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        setRequest(request);
//        String requestBody = HttpHelper.getRequestBody(request);
//        body = requestBody.getBytes(Charset.forName("UTF-8"));
    }

    public Item setRequest(HttpServletRequest request){
        return new Item(request);
    }

//    @Override
//    public ServletInputStream getInputStream() {
//        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
//        return new ServletInputStream() {
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//
//            @Override
//            public void setReadListener(ReadListener readListener) {
//
//            }
//
//            @Override
//            public int read() {
//                return inputStream.read();
//            }
//        };
//    }
}
