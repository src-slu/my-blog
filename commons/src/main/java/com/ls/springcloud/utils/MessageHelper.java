package com.ls.springcloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName MessageHelper
 * @Description mq消息转化类
 * @Author lushuai
 * @Date 2019/11/20 14:53
 */
@Slf4j
public class MessageHelper {

    /**
     * 对象转为消息
     * @param obj
     * @return
     */
    public static Message objToMsg(Object obj){
        if (obj == null){
            return null;
        }
        Message message = null;
        try {
            message = MessageBuilder.withBody(JsonUtils.objToString(obj).getBytes("utf-8")).build();
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT); //消息持久化
            message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
        } catch (UnsupportedEncodingException e) {
            log.warn("error caused by [{}]", e);
        }
        return message;
    }

    /**
     * 消息转为对象
     * @param <T>
     * @param message
     * @param clazz
     * @return
     */
    public static <T> T msgToObj(Message message, Class<T> clazz){
        if(message == null || clazz == null){
            return null;
        }
        String str = new String(message.getBody());
        return JsonUtils.strToObject(str, clazz);
    }
}
