package com.ls.springcloud.task;

import com.ls.springcloud.pojo.MsgLog;
import com.ls.springcloud.service.MsgLogService;
import com.ls.springcloud.utils.Constant;
import com.ls.springcloud.utils.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ReSendMsgTask
 * @Description 重新发送超时的消息
 * @Author lushuai
 * @Date 2019/11/27 9:42
 */
@Component
@Slf4j
public class ReSendMsgTask {

    /* 最大重试投递次数5次 */
    private static final int MAX_DELIVER_TIMES = 5;

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private RabbitTemplate template;

    @Scheduled(cron = "0/30 * * * * ?")
    public void resendMsg(){
        log.info("定时任务执行开始(重新投递消息)....");
        List<MsgLog> msgLogList = msgLogService.findDeliverFailMsg();
        msgLogList.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if(msgLog.getTryCount() > MAX_DELIVER_TIMES){
                /**
                 * 重新投递次数达到最大值还未成功，设置为投递失败
                 */
                msgLogService.updateStatus(msgId, Constant.MsgLogStatus.DELIVER_FAIL);
                log.warn("超过最大投递次数，投递失败。消息ID: [{}]", msgId);
            }else{
                /**
                 * 投递次数 +1
                 */
                msgLogService.updateTryCount(msgId, msgLog.getNextTryTime());

                /**
                 * 重新投递
                 */
                CorrelationData correlationData = new CorrelationData(msgId);
                template.convertAndSend(msgLog.getExchange(),
                        msgLog.getRoutingKey(), MessageHelper.objToMsg(msgLog.getMsg()), correlationData);
                log.info("第[{}]次重新投递消息", msgLog.getTryCount() + 1);
            }
        });
        log.info("定时任务执行结束(重新投递消息)....");
    }
}
