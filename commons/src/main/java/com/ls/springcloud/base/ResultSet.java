package com.ls.springcloud.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ls.springcloud.utils.ResponseCode;

/**
 * @ClassName ResultSet
 * @Description 统一返回类型
 * @Author lushuai
 * @Date 2019/11/14 12:34
 */
public class ResultSet<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public ResultSet(){
        if(data == null){
            new ResultSet(this.getCode(), this.getMsg());
        }
    }

     public ResultSet(int code, String msg){
        this.code = code;
        this.msg = msg;
     }

    public ResultSet<T> setCode(ResponseCode responseCode) {
        this.code = responseCode.code;
        return this;
    }

    public int getCode(){
        return this.code;
    }

    public ResultSet<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg(){
        return this.msg;
    }

    public ResultSet<T> setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public ResultSet<T> setData(T data) {
        this.data = data;
        return this;
    }

}
