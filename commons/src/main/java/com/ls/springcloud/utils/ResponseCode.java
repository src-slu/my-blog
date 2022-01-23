package com.ls.springcloud.utils;

import org.springframework.stereotype.Component;

/**
 * @ClassName ResponseCode
 * @Description 响应状态码
 * @Author lushuai
 * @Date 2019/11/14
 */
public enum ResponseCode {
        // 成功
        SUCCESS(200),

        // 失败
        FAIL(400),

        // 未认证（签名错误）
        UNAUTHORIZED(401),

        // 接口不存在
        NOT_FOUND(404),

        // 服务器内部错误
        INTERNAL_SERVER_ERROR(500);


        public int code;

//        public String msg;w

        ResponseCode(int code) {
                this.code = code;
        }

//        ResponseCode(int code, String msg){
//                this.code = code;
//                this.msg = msg;
//        }
//
//        public int getCode() {
//                return code;
//        }
//
//        public void setCode(int code) {
//                this.code = code;
//        }
//
//        public String getMsg() {
//                return msg;
//        }
//
//        public void setMsg(String msg) {
//                this.msg = msg;
//        }

}
