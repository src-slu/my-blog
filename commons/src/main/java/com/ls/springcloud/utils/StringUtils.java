package com.ls.springcloud.utils;

import com.ls.springcloud.utils.FixValue;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @ClassName StringUtils
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 10:18
 */
@Slf4j
public class StringUtils {

    public static long getLong(String param){
        if(param == null){
            return 0;
        }
        param = param.trim();
        int index = param.indexOf(".");
        if(index >= 0){
            param = param.substring(0, index);
        }
        param = lTrim(param, '0');
        if("".equals(param) || FixValue.NULL.equals(param.toLowerCase())){
            return 0;
        }
        long ret = 0;
        try {
            ret = Long.parseLong(param);
        }catch(Exception e){
            log.error("{}", e);
        }
        return ret;
    }

    public static int getInteger(String param){
        if(param == null){
            return 0;
        }
        param = param.trim();
        int index = param.indexOf(".");
        if(index >= 0){
            param = param.substring(0, index);
        }
        param = lTrim(param, '0');
        if("".equals(param) || FixValue.NULL.equals(param.toLowerCase())){
            return 0;
        }
        int ret = 0;
        try {
            ret = Integer.parseInt(param);
        }catch(Exception e){
            log.error("{}", e);
        }
        return ret;
    }

    public static double getDouble(String param){
        if(param == null){
            return 0;
        }
        param = param.trim();
        int index = param.indexOf(".");
        if(index >= 0){
            param = param.substring(0, index);
        }
        param = lTrim(param, '0');
        if("".equals(param) || FixValue.NULL.equals(param.toLowerCase())){
            return 0;
        }
        double ret = 0;
        try {
            ret = Double.parseDouble(param);
        }catch(Exception e){
            log.error("{}", e);
        }
        return ret;
    }

    public static String lTrim(String str,char c) {
        if(str == null || str.equals("")) {
            return "";
        }
        char[] list = str.toCharArray();
        int idx = 0;
        while(idx < list.length) {
            if(list[idx] == c) {
                idx ++;
            }
            else {
                break;
            }
        }
        if(idx ==0 ) {
            return str;
        }
        return new String(list,idx,list.length - idx);
    }

    /**
     * 给指定的字符串排序
     * @param str
     * @return
     */
    public static String sortStr(String str){
        char[] chars = str.toCharArray();
        Arrays.sort(chars);

        return "";
    }
}
