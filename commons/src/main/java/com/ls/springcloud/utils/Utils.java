package com.ls.springcloud.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName Utils
 * @Description
 * @Author lushuai
 * @Date 2020/4/14 15:24
 */
public class Utils {

    public static String getAllGetSet(Class<?> clazz, Object ... bean) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sBuilder = new StringBuilder();

        for(Field field : fields){

            // 字段首字母大写
            String key = field.getName().substring(0,1).toUpperCase();
            if(!"".equals(sBuilder.toString())){
                sBuilder.append(",");
            }
            String method = "set" + key + field.getName().substring(1);
            String type = field.getGenericType().toString();
            /**
             * 如果是String类型
             */
            if("class java.lang.String".equals(type)){
                Method method_string = clazz.getClass().getMethod(method);
//                if(bean.){
//
//                }
                method_string.invoke(clazz);
            }

        }

        return sBuilder.toString();
    }
}
