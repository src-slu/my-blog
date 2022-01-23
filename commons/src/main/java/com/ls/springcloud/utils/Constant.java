package com.ls.springcloud.utils;

/**
 * @ClassName Constant
 * @Description
 * @Author lushuai
 * @Date 2019/11/20 10:53
 */
public class Constant {

    public interface MsgLogStatus {
        /**
         * 状态: 0未投递 1投递成功 2投递失败 3已消费
         */
        Integer DELIVERING = 0;// 消息未投递
        Integer DELIVER_SUCCESS = 1;// 投递成功
        Integer DELIVER_FAIL = 2;// 投递失败
        Integer CONSUMED_SUCCESS = 3;// 已消费
    }

    public interface RabbitMQConfig {
         // 队列名称
        String MAIL_QUEUE_NAME = "mail.queue";

        // 交换机名称
        String MAIL_EXCHANGE_NAME = "mail.exchange";

        // 路由
        String MAIL_ROUTING_KEY_NAME = "mail.routing.key";

        // 死信队列交换机
        String DEAD_LETTER_EXCHANGE_NAME  = "dead.letter.exchange";

        // 死信队列路由key
        String DEAD_LETTER_ROTING_KEY_NAME = "dead.letter.routing.key";

        // 死信队列名称
        String DEAD_LETTER_QUEUE_NAME = "dead.letter.queue";


        String DEAD_REDIRECT_QUEUE_NAME = "dead.redirect.queue";

        String DEAD_REDIRECT_ROUTING_KEY_NAME = "dead.redirect.routing.key";
    }

}