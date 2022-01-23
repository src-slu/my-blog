package com.ls.springcloud.mapper;

import com.ls.springcloud.pojo.MsgLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName MsgLogMapper
 * @Description
 * @Author lushuai
 * @Date 2019/11/19 16:19
 */
@Repository
public interface MsgLogMapper {

    /**
     * 根据消息Id查询日志记录
     * @param msgId
     * @return
     */
    MsgLog selectById(@Param("msgId") String msgId);

    /**
     * 插入消息日志
     * @param msgLog
     * @return
     */
    boolean insertMsgLog(MsgLog msgLog);

    /**
     * 更改日志状态
     * @param msgLog
     * @return
     */
    int updateStatus(MsgLog msgLog);

//    /**
//     * 根据消息Id查询日志记录
//     * @param msgId
//     * @return
//     */
//    MsgLog selectById(@Param("msgId")String msgId);

    /**
     * 查询未成功投递的消息
     * @return
     */
    List<MsgLog> selectDeliverFailMsg();

    /**
     * 更新重试次数
     * @param msgLog
     * @return
     */
    int updateTryCount(MsgLog msgLog);
}
