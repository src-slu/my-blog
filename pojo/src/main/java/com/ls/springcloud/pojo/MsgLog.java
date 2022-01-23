package com.ls.springcloud.pojo;

import com.ls.springcloud.utils.Constant;
import com.ls.springcloud.utils.DateTimeUtils;
import com.ls.springcloud.utils.JsonUtils;
import lombok.Data;

import java.util.Date;


/**
 * @Title MsgLog
 * @Description 消息日志类
 * @Author lush
 */
@Data
public class MsgLog {

  private String msgId;
  private String msg;
  private String exchange;
  private String routingKey;
  private Integer status;
  private Integer tryCount;
  private Date nextTryTime;
  private Date createTime;
  private Date updateTime;

  public MsgLog(){}

  public MsgLog(String msgId, Object msg, String exchange, String routingKey) {
    this.msgId = msgId;
    this.msg = JsonUtils.objToString(msg);
    this.exchange = exchange;
    this.routingKey = routingKey;

    this.status = Constant.MsgLogStatus.DELIVERING;
    this.tryCount = 0;

    Date date = new Date();
    this.createTime = date;
    this.updateTime = date;
    this.nextTryTime = (DateTimeUtils.plusMinutes(date, 1));
  }

}
