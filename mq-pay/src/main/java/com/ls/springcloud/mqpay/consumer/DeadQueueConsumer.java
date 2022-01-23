package com.ls.springcloud.mqpay.consumer;

import com.ls.springcloud.utils.Constant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName DeadQueueConsumer
 * @Description
 * @Author lushuai
 * @Date 2020/5/6 16:28
 */
@Slf4j
@Component
public class DeadQueueConsumer {


//    @RabbitListener(queuesToDeclare = @Queue(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME))
    @RabbitHandler
    @RabbitListener(queues = {Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME})
    public void consumer(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("dead message  10s 后 消费消息 {}", new String(message.getBody()));
    }

//    @RabbitHandler
//    @RabbitListener(queuesToDeclare = @Queue(Constant.RabbitMQConfig.DEAD_LETTER_QUEUE_NAME))
//    public void deadConsumer(Message message, Channel channel) throws IOException {
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        log.info("dead message will received after 10s [{}]", new String(message.getBody()));
//    }
}
