package com.ls.springcloud.mqpay.config;

import com.ls.springcloud.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeadMqConfig
 * @Description
 * @Author lushuai
 * @Date 2020/5/7 16:36
 */
@Configuration
@Slf4j
public class DeadLetterMqConfig {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Bean
    public AmqpTemplate template() {

        // 设置编码集
        rabbitTemplate.setEncoding("utf-8");
        // 设置json解析器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 开启returnCallback，需要配置文件开启publisher-returns: true
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("消息发送到交换机成功: 消息ID {}", correlationData.getId());
            } else {
                log.debug("消息发送到交换机失败: 消息ID {}, 原因: {}", correlationData.getId(), cause);
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息: {} 发送失败，应答码: {}, 原因: {}, 交换机: {}, 路由键: {}",
                    correlationId, replyCode, replyText, exchange, routingKey);
        });

        return rabbitTemplate;
    }

    /**
     * 声明死信交换机
     *
     * @return
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 声明死信队列
     *
     * @return
     */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue() {
        Map<String, Object> argments = new HashMap<>();
        argments.put("x-dead-letter-exchange", Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME);
//        argments.put("x-dead-letter-routing-key", Constant.RabbitMQConfig.DEAD_LETTER_ROTING_KEY_NAME);
        argments.put("x-dead-letter-routing-key", Constant.RabbitMQConfig.DEAD_REDIRECT_ROUTING_KEY_NAME);
//        argments.put("x-message-ttl", 10000);
        return QueueBuilder.durable(Constant.RabbitMQConfig.DEAD_LETTER_QUEUE_NAME)
                .withArguments(argments).build();
    }

    /**
     * 声明转发交换机
     * @return
     */
    @Bean("redirectQueue")
    public Queue redirectQueue() {
        return QueueBuilder.durable(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME).build();
    }

    /**
     * 绑定死信队列
     * @return
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding(Constant.RabbitMQConfig.DEAD_LETTER_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME,
                Constant.RabbitMQConfig.DEAD_LETTER_ROTING_KEY_NAME, null);
    }

    /**
     * 绑定转发队列
     * @return
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME,
                Constant.RabbitMQConfig.DEAD_REDIRECT_ROUTING_KEY_NAME, null);
    }
}
