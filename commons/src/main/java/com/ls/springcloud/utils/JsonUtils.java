package com.ls.springcloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName JsonUtils
 * @Description
 * @Author lushuai
 * @Date 2019/11/20 14:29
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper mapper = null;

    static{
        mapper = new ObjectMapper();
    }

    /**
     * 对象转化为字符串
     * @param data
     * @param <T>
     * @return
     */
    public static <T> String objToString(T data) {
        if (data == null) {
            return null;
        }
        try {
            return data instanceof String ? (String) data : mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.warn("对象转化为String失败 [{}], cause [{}]", e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * String 转化为对象
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T strToObject(String data, Class<T> clazz){
        if("".equals(data) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) data : mapper.readValue(data, clazz);
        } catch (IOException e) {
            log.warn("String转化对象失败 [{}], cause [{}]", e.getMessage(), e.getCause());
        }
        return null;
    }

    /**
     * String转list
     * @param data
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T strToList(String data, TypeReference<T> reference){
        try {
            return (T) (reference.getType().equals(String.class) ? data : mapper.readValue(data, reference));
        } catch (IOException e) {
            log.warn("String转化List失败 [{}], cause [{}]", e.getMessage(), e.getCause());
        }
        return null;
    }
}
