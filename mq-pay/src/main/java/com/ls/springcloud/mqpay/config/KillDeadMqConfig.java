//package com.ls.springcloud.mqpay.config;
//
//import com.ls.springcloud.utils.Constant;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @ClassName KillMqConfig
// * @Description
// * @Author lushuai
// * @Date 2020/5/6 13:14
// */
////@Configuration(value = "KillDeadMqConfig")
//@EnableRabbit
//public class KillDeadMqConfig {
//
//    /**
//     * 创建配置死信队列
//     * @return
//     */
//    @Bean
//    public Queue creatKillDeadQueue(){
//        // 队列出现dead letter的情况有：
//        //消息或者队列的TTL过期
//        //队列达到最大长度
//        //消息被消费端拒绝（basic.reject or basic.nack）并且requeue=false
//        Map<String, Object> map = new HashMap<>();
//        // 出现dead letter之后将dead letter重新发送到指定exchange
//        map.put("x-dead-letter-exchange", Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME);
//        // 出现dead letter之后将dead letter重新按照指定的routing-key发送
//        map.put("x-dead-letter-routing-key", Constant.RabbitMQConfig.DEAD_LETTER_ROTING_KEY_NAME);
//        return QueueBuilder.nonDurable(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME).withArguments(map).build();
//    }
//
//    /**
//     * 设置死信队列转发队列
//     * @return
//     */
//    @Bean("redirectQueue")
//    public Queue redirectQueue(){
//        return QueueBuilder.durable(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME).build();
//    }
//
//    /**
//     * 死信路由通过交换机绑定到交换机上
//     * @return
//     */
//    @Bean
//    public Binding binding(){
//        return new Binding(Constant.RabbitMQConfig.DEAD_LETTER_QUEUE_NAME,
//                Binding.DestinationType.QUEUE, Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME,
//                Constant.RabbitMQConfig.DEAD_LETTER_ROTING_KEY_NAME, null);
//    }
//
//    /**
//     * 死信队列通过路由绑定到转发队列
//     * @return
//     */
//    @Bean
//    public Binding redirectBinding(){
//        return new Binding(Constant.RabbitMQConfig.DEAD_REDIRECT_QUEUE_NAME,
//                Binding.DestinationType.QUEUE, Constant.RabbitMQConfig.DEAD_LETTER_EXCHANGE_NAME,
//                Constant.RabbitMQConfig.DEAD_REDIRECT_ROUTING_KEY_NAME, null);
//    }
//
//}
