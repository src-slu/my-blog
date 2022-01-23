package com.ls.springcloud.pojo;

import lombok.Data;

/**
 * @ClassName Email
 * @Description
 * @Author lushuai
 * @Date 2019/11/11 10:24
 */
@Data
public class Email {
    /**
     * 收件人
     * 发件人
     * 标题
     * 内容
     * 附件
     */
    private String msgId;
    private String to;
    private String from;
//    private String theme;
    private String title;
    private String content;
    private String file;

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("\r\n[");
        builder.append("\r\n   发件人: " + from);
        builder.append("\r\n   收件人: " + to);
        if(title != null && !"".equals(title)){
            builder.append("\r\n    标题: " + title);
        }
        if(content != null && !"".equals(content)){
            builder.append("\r\n    内容: " + content);
        }
        if(file != null && !"".equals(file)){
            builder.append("\r\n    附件: " + file);
        }
        builder.append("\r\n]");

        return builder.toString();
    }

}
