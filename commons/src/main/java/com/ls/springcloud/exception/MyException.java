package com.ls.springcloud.exception;

import lombok.Data;

/**
 * @ClassName MyException
 * @Description 自定义异常信息
 * @Author lushuai
 * @Date 2019/12/30 13:48
 */
public class MyException extends RuntimeException{

    private static final long serialVersionUID = -7328717057988285061L;

    /**
     * 错误头信息
     */
    private String errorTitle;
    /**
     * 发生的错误类型
     */
    private Throwable errorType;
    /**
     * 发生错误的类
     */
    private String errorClass;
    /**
     * 发生错误的方法
     */
    private String errorMethod;
    /**
     * 发生错误的原因
     */
    private String errorMessage;
    /**
     * 发生的错误所在行
     */
    private int errorNumber;

    public MyException(){
        super();
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public MyException setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
        return this;
    }

    public Throwable getErrorType() {
        return errorType;
    }

    public MyException setErrorType(Throwable errorType) {
        this.errorType = errorType;
        return this;
    }

    public String getErrorClass() {
        return errorClass;
    }

    public MyException setErrorClass(String errorClass) {
        this.errorClass = errorClass;
        return this;
    }

    public String getErrorMethod() {
        return errorMethod;
    }

    public MyException setErrorMethod(String errorMethod) {
        this.errorMethod = errorMethod;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public MyException setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public MyException setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
        return this;
    }

    @Override
    public String toString() {
        return "MyException { " + errorTitle
                + "\n errorType: '" + errorType
                + "\n errorClass: '" + errorClass
                + "\n errorMethod: '" + errorMethod
                + "\n errorMessage: '" + errorMessage
                + "\n errorNumber:" + errorNumber
                + "}";
    }
}
