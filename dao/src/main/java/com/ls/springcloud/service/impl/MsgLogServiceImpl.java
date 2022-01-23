package com.ls.springcloud.service.impl;

import com.ls.springcloud.base.Response;
import com.ls.springcloud.base.ResultSet;
import com.ls.springcloud.pojo.Email;
import com.ls.springcloud.pojo.MsgLog;
import com.ls.springcloud.utils.Constant;
import com.ls.springcloud.utils.DateTimeUtils;
import com.ls.springcloud.utils.MessageHelper;
import com.ls.springcloud.utils.SnowflakeIdWorker;
import com.ls.springcloud.mapper.MsgLogMapper;
import com.ls.springcloud.service.MsgLogService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MsgLogServiceImpl
 * @Description 消息生产者
 * @Author lushuai
 * @Date 2019/11/21 9:08
 */
@Service
@Transactional
public class MsgLogServiceImpl implements MsgLogService {


    @Autowired
    private RabbitTemplate template;

    @Autowired
    private MsgLogMapper msgLogMapper;

    /**
     * 发送邮件
     *
     * @param email
     * @return
     */
    @Override
    public ResultSet send(Email email) {
        /**
         * 通过雪花算法生成id
         */
        String msgId = SnowflakeIdWorker.getSnowFlackId();
        email.setMsgId(msgId);
        MsgLog msgLog = new MsgLog(msgId, email,
                Constant.RabbitMQConfig.MAIL_EXCHANGE_NAME,
                Constant.RabbitMQConfig.MAIL_ROUTING_KEY_NAME);

        /**
         * 保存消息日志
         */
        msgLogMapper.insertMsgLog(msgLog);
        CorrelationData correlationData = new CorrelationData(msgId);
        // 发送
        template.convertAndSend(Constant.RabbitMQConfig.MAIL_EXCHANGE_NAME,
                Constant.RabbitMQConfig.MAIL_ROUTING_KEY_NAME,
                MessageHelper.objToMsg(email),
                correlationData);
        return Response.okResponse();
    }

    /**
     * 根据Id查询日志记录
     *
     * @param msgId
     * @return
     */
    @Override
    public MsgLog selectById(String msgId) {
        return msgLogMapper.selectById(msgId);
    }

    /**
     * 更新消息日志状态
     *
     * @param msgId
     * @param status
     * @return
     */
    @Override
    public int updateStatus(String msgId, Integer status) {
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setStatus(status);
        msgLog.setUpdateTime(new Date());
        return msgLogMapper.updateStatus(msgLog);
    }

    /**
     * 查寻未成功投递的消息
     *
     * @return
     */
    @Override
    public List<MsgLog> findDeliverFailMsg() {
        return msgLogMapper.selectDeliverFailMsg();
    }

    /**
     * 更新下次投递时间
     *
     * @param msgId
     * @param nextTryDate
     * @return
     */
    @Override
    public int updateTryCount(String msgId, Date nextTryDate) {
        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setNextTryTime(DateTimeUtils.plusMinutes(nextTryDate, 1));
        return msgLogMapper.updateTryCount(msgLog);
    }
}
