package com.ls.springcloud.config;

import com.ls.springcloud.service.MsgLogService;
import com.ls.springcloud.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @ClassName RabbitMQConfig
 * @Description
 * @Author lushuai
 * @Date 2019/11/19 15:23
 */
@Slf4j
@Configuration(value = "RabbitMQConfig")
@EnableRabbit
public class RabbitMQConfigCenter {

    @Autowired
    private CachingConnectionFactory factory;

    @Autowired
    private MsgLogService msgLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(converter());

        template.setConfirmCallback((correlationData, ack, cause) ->{
            String id = correlationData.getId();
            if(ack){
                log.info("消息成功发送到Exchange");
                msgLogService.updateStatus(id, Constant.MsgLogStatus.DELIVER_SUCCESS);
                log.info("消息ID [{}]", id);
            }else{
                msgLogService.updateStatus(id, Constant.MsgLogStatus.DELIVER_FAIL);
                log.error("消息发送到Exchange失败 [{}], cause [{}]", correlationData, cause);
            }
        });
        /**
         * 触发setReturnCallback回调必须设置mandatory=true,
         * 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
         */
        template.setMandatory(true);
        /**
         * 消息是否从Exchange路由到Queue,
         * 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
         */
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->{
            log.info("消息从Exchange路由到Queue失败: " +
                    "exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}",
                    exchange, routingKey, replyCode, replyText, message);
        });
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue mailQueue(){
        return new Queue(Constant.RabbitMQConfig.MAIL_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(Constant.RabbitMQConfig.MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding mailBind(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(Constant.RabbitMQConfig.MAIL_ROUTING_KEY_NAME);
    }

}

