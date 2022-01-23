package com.ls.springcloud.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName DataBase
 * @Description
 * @Author lushuai
 * @Date 2019/11/13 9:50
 */
public class CommandData extends HashMap implements Map {

    private static final long serialVersionUID = 1L;

    private static HttpServletRequest request;
    private HttpServletResponse response;
    private Map<String, Object> paramMap = new HashMap<>();

    public CommandData(HttpServletRequest request){
        this.request = request;
        Map<String, String[]> parameterMap = request.getParameterMap();
        Iterator iterator = parameterMap.entrySet().iterator();
//        Iterator<String> iterator = strings.iterator();
        Entry entries;
        String key;
        String value = "";

        while(iterator.hasNext()) {
            entries = (Entry) iterator.next();
            key = (String) entries.getKey();
            Object objValue = entries.getValue();
            if(objValue == null){
                value = "";
            }
            else if(objValue instanceof String[]){
                String values[] = (String[]) objValue;
                for(int i=0; i<values.length; i++){
                    value = values[i] +",";
                }
                value = value.substring(0, value.length() - 1);
            }else{
                value = objValue.toString();
            }
            paramMap.put(key, value);
        }
    }

    public void setResponse(HttpServletResponse response){
        this.response = response;
    }

    public HttpServletResponse getResponse(){
        return this.response;
    }

    public static void setSession(String key, Object value){
        request.getSession().setAttribute(key, value);
    }

    public static void removeSession(String key){
        request.getSession().removeAttribute(key);
    }

//    public String getStringColumn(String key){
//        if(){
//
//        }
//        return paramMap.get("key").toString();
//    }
}
