package com.ls.springcloud.service;

import com.ls.springcloud.base.ResultSet;
import com.ls.springcloud.pojo.Email;
import com.ls.springcloud.pojo.MsgLog;

import java.util.Date;
import java.util.List;

/**
 * @ClassName MsgLogService
 * @Description
 * @Author lushuai
 * @Date 2019/11/19 16:37
 */
public interface MsgLogService {

    /**
     * 根据Id查询日志记录
     * @param msgId
     * @return
     */
    MsgLog selectById(String msgId);

    /**
     * 发送邮件
     * @param email
     * @return
     */
    ResultSet send(Email email);

    /**
     * 更新消息日志状态
     * @param msgId
     * @param status
     * @return
     */
    int updateStatus(String msgId, Integer status);

    /**
     * 查寻未成功投递的消息
     * @return
     */
    List<MsgLog> findDeliverFailMsg();

    /**
     * 更新下次重试投递时间
     * @param msgId
     * @param nextTryDate
     * @return
     */
    int updateTryCount(String msgId, Date nextTryDate);
}
