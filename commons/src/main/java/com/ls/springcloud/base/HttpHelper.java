package com.ls.springcloud.base;

import com.sun.management.OperatingSystemMXBean;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;

/**
 * @ClassName HttpHelper
 * @Description
 * @Author lushuai
 * @Date 2019/11/14 14:58
 */
public class HttpHelper {

    public static String getRequestBody(HttpServletRequest request){
        StringBuilder sbf = new StringBuilder();
        ServletInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            char[] bytes = new char[1024];
            int length;
            while( (length = reader.read(bytes)) != -1){
                sbf.append(new String(bytes, 0, length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static void main(String[] args) {
        System.out.println("================ 操作系统 ==================");
        String property = System.getProperty("os.name");
        System.out.println(property);
        System.out.println("================ 线程信息 ==================");

        OperatingSystemMXBean osmxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//        osmxbean.getArch()
        System.out.println("============= 系统框架 =============");
        String name = osmxbean.getArch();
        System.out.println(name);

        System.out.println("============= 版本信息 ==================");
        String version = osmxbean.getVersion();
        System.out.println(version);

        System.out.println("============= 平均使用频率 =============");
        double average = osmxbean.getSystemLoadAverage();
        System.out.println(average);

        System.out.println("============= 内存信息 =============");
//        long totalPhysicalMemorySize = ;
        System.out.println("物理内存总量：" + osmxbean.getTotalPhysicalMemorySize() /1024.0 /1024 /1024 +"G");
        System.out.println("未使用物理内存：" + osmxbean.getFreePhysicalMemorySize() /1024.0 /1024 /1024 +"G");
        System.out.println("挂在内存总量：" + osmxbean.getTotalSwapSpaceSize() /1024.0 /1024 /1024 +"G");

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name1 = runtimeMXBean.getName();
        System.out.println("jvm名称：" + name1);
        long startTime = runtimeMXBean.getStartTime();
        System.out.println("jvm启动时间：" + startTime /1000 /60 /60 + "分");



    }
}
