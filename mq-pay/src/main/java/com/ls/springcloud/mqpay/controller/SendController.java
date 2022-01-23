package com.ls.springcloud.mqpay.controller;

import com.ls.springcloud.base.Response;
import com.ls.springcloud.base.ResultSet;
import com.ls.springcloud.utils.Constant;
import com.ls.springcloud.utils.SnowflakeIdWorker;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName SendController
 * @Description
 * @Author lushuai
 * @Date 2020/5/6 15:49
 */
@RestController
@RequestMapping("rabbit")
public class SendController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/dead")
    public ResultSet deadLetter(String msg){
        CorrelationData correlationData = new CorrelationData(SnowflakeIdWorker.getSnowFlackId());
        MessagePostProcessor postProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间 10 * 1000毫秒
            messageProperties.setExpiration("10000");
            return message;
        };
        rabbitTemplate.convertAndSend(Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME,
                Constant.RabbitMQConfig.DEAD_LETTER_ROTING_KEY_NAME, msg,
                postProcessor, correlationData);
        return Response.okResponse("发送成功");
    }
}
