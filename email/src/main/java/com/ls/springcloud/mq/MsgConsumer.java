package com.ls.springcloud.mq;

import com.ls.springcloud.pojo.Email;
import com.ls.springcloud.pojo.MsgLog;
import com.ls.springcloud.service.MsgLogService;
import com.ls.springcloud.utils.Constant;
import com.ls.springcloud.utils.MessageHelper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @ClassName MsgConsumer
 * @Description
 * @Author lushuai
 * @Date 2019/12/10 14:53
 */
@Component
@Slf4j
public class MsgConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailUtils mailUtils;

    //queues = Constant.RabbitMQConfig.MAIL_QUEUE_NAME

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue(Constant.RabbitMQConfig.MAIL_QUEUE_NAME))
    public void consumer(Message message, Channel channel) throws IOException {
        Email email = MessageHelper.msgToObj(message, Email.class);
        String msgId = email.getMsgId();
        /**
         * 通过msgId查看消息状态
         */
        MsgLog msgLog = msgLogService.selectById(msgId);
        if(msgLog == null || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)){
            log.warn("重复消费, msgId: [{}]", msgId);
            return;
        }
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();

        /**
         * 发送mq中的邮件
         */
        boolean sendResult = mailUtils.send(email);
        if (sendResult) {
            /**
             * 消息发送成功，手动ack确认，并删除mq中该条消息
             */
            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(deliveryTag, false);
        } else {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
