package com.ls.springcloud.base;

import java.io.IOException;

/**
 * @author lushuai
 */
public class ScriptUtils {
    private static final Runtime runtime = Runtime.getRuntime();

    public static void execScript(String script) {
        Process process = null;
        try {
            // 指示java虚拟机创建一个子进程执行脚本，并返回执行子线程对应的process的实例
            process = runtime.exec(script);
            // 等待子进程执行完成再往下执行
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }finally {
            assert process != null;
            int exitValue = process.exitValue();
            System.out.println("脚本执行结果: " + (exitValue == 0 ? "执行完成": "执行失败"));
            process.destroy();
        }
    }
}
