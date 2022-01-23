package com.ls.springcloud.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName MyException
 * @Description 自定义异常解析
 * @Author lushuai
 * @Date 2019/12/30 13:36
 */
@Slf4j
public class MyExceptionResolver {

    /**
     * 获取产生异常的原因、类、方法、错误所在行
     * 输出错误信息
     * @param e
     */
    public MyException printException(String describe, Exception e) {
        /**
         * 获取错误首元素
         */
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        /**
         * 获取错误信息
         */
        String message = stackTraceElement.toString();
        /**
         * 获取错误产生的类
         */
        String className = stackTraceElement.getClassName();
        /**
         * 获取发生异常的方法
         */
        String methodName = stackTraceElement.getMethodName();
        /**
         * 获取异常产生的行数
         */
        int lineNumber = stackTraceElement.getLineNumber();

        MyException myException = new MyException().setErrorTitle(describe).setErrorType(e.getCause()).setErrorClass(className)
                .setErrorMethod(methodName).setErrorNumber(lineNumber).setErrorMessage(message);

        log.error(describe, myException);
        return myException;
    }
}
