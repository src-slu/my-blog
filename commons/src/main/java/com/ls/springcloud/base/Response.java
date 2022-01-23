package com.ls.springcloud.base;

import com.ls.springcloud.utils.ResponseCode;

/**
 * @ClassName ResponseSet
 * @Description 统一响应
 * @Author lushuai
 * @Date 2019/11/14 13:46
 */
public class Response {

    private static final String SUCCESS = "success";

    /**
     * 响应成功，并返回success
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> okResponse(){
        return new ResultSet<T>().setCode(ResponseCode.SUCCESS).setMsg(SUCCESS);
    }

    /**
     * 响应成功，返回成功信息
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> okResponse(String msg){
        return new ResultSet<T>().setCode(ResponseCode.SUCCESS).setMsg(msg);
    }

    /**
     * 响应成功，并返回数据
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> okResponse(T data){
        return new ResultSet<T>().setCode(ResponseCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    /**
     * 响应失败，返回错误信息
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> errorResponse(String msg){
        return new ResultSet<T>().setCode(ResponseCode.FAIL).setMsg(msg);
    }

    /**
     * 自定义响应code，信息
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> response(int code, String msg){
        return new ResultSet<T>().setCode(code).setMsg(msg);
    }

    /**
     * 自定义响应code，信息，返回数据
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultSet<T> response(int code, String msg, T data){
        return new ResultSet<T>().setCode(code).setMsg(msg).setData(data);
    }

}
